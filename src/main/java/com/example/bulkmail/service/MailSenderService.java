package com.example.bulkmail.service;

import com.example.bulkmail.model.MailBatch;
import com.example.bulkmail.model.MailItem;
import com.example.bulkmail.repo.MailBatchRepository;
import com.example.bulkmail.repo.MailItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailBatchRepository mailBatchRepository;

    @Autowired
    private MailItemRepository mailItemRepository;

    @Async
    public void sendBulkEmails(MailBatch mailBatch) {
        mailBatch.setStatus("SENDING");
        mailBatchRepository.save(mailBatch);

        List<MailItem> mailItems = mailBatch.getMailItems();
        for (MailItem mailItem : mailItems) {
            try {
                sendEmail(mailItem);
                mailItem.setStatus("SENT");
            } catch (Exception e) {
                mailItem.setStatus("FAILED");
                mailItem.setErrorMessage(e.getMessage());
            }
            mailItem.setMailBatch(mailBatch);
            mailItemRepository.save(mailItem);
        }

        mailBatch.setStatus("SENT");
        mailBatchRepository.save(mailBatch);
    }

    private void sendEmail(MailItem mailItem) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setTo(mailItem.getEmail());
        helper.setSubject(mailItem.getSubject());
        helper.setText(mailItem.getMessage(), false);

        // If attachment file path is specified, attach the file
        if (mailItem.getAttachmentFile() != null && !mailItem.getAttachmentFile().isEmpty()) {
            java.io.File file = new java.io.File(mailItem.getAttachmentFile());
            if (file.exists()) {
                helper.addAttachment(file.getName(), file);
            }
        }
        mailSender.send(mimeMessage);
    }
}
