package com.example.bulkmail.service;

import com.example.bulkmail.model.MailBatch;
import com.example.bulkmail.model.MailItem;
import com.example.bulkmail.repo.MailBatchRepository;
import com.example.bulkmail.repo.MailItemRepository;
import com.example.bulkmail.repo.MailLogRepository;
import com.example.bulkmail.model.MailLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import jakarta.mail.internet.MimeMessage;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class MailSenderService {
    private static final Logger logger = LoggerFactory.getLogger(MailSenderService.class);
    
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailBatchRepository mailBatchRepository;

    @Autowired
    private MailItemRepository mailItemRepository;

    @Autowired
    private MailLogRepository mailLogRepository;
    
    @Value("${spring.mail.from:learn.test0000@gmail.com}")
    private String fromEmail;

    @Async
    public void sendBulkEmails(MailBatch mailBatch) {
        logger.info("Starting to send bulk emails for batch ID: {}", mailBatch.getId());
        mailBatch.setStatus("SENDING");
        mailBatchRepository.save(mailBatch);

        List<MailItem> mailItems = mailBatch.getMailItems();
        int successCount = 0;
        int failureCount = 0;
        
        for (MailItem mailItem : mailItems) {
            try {
                sendEmail(mailItem);
                mailItem.setStatus("SENT");
                successCount++;
                logger.info("Successfully sent email to: {}", mailItem.getEmail());
            } catch (Exception e) {
                mailItem.setStatus("FAILED");
                mailItem.setErrorMessage(e.getMessage());
                failureCount++;
                logger.error("Failed to send email to: {}. Error: {}", mailItem.getEmail(), e.getMessage(), e);
            }
            mailItem.setMailBatch(mailBatch);
            mailItemRepository.save(mailItem);

            // Log the sent email
            MailLog log = new MailLog();
            log.setEmail(mailItem.getEmail());
            log.setBatchId(mailBatch.getId());
            log.setDetails("Subject: " + mailItem.getSubject() + ", Message: " + mailItem.getMessage() + ", Status: " + mailItem.getStatus() + (mailItem.getAttachmentFile() != null ? ", Attachment: " + mailItem.getAttachmentFile() : "") + (mailItem.getErrorMessage() != null ? ", Error: " + mailItem.getErrorMessage() : ""));
            mailLogRepository.save(log);
        }

        mailBatch.setStatus("SENT");
        mailBatchRepository.save(mailBatch);
        logger.info("Completed bulk email sending for batch ID: {}. Success: {}, Failures: {}", mailBatch.getId(), successCount, failureCount);
    }

    private void sendEmail(MailItem mailItem) throws Exception {
        logger.debug("Preparing to send email to: {}", mailItem.getEmail());
        
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        
        helper.setFrom(fromEmail);
        helper.setTo(mailItem.getEmail());
        helper.setSubject(mailItem.getSubject());
        helper.setText(mailItem.getMessage(), false);

        // If attachment file path is specified, attach the file
        if (mailItem.getAttachmentFile() != null && !mailItem.getAttachmentFile().isEmpty()) {
            java.io.File file = new java.io.File(mailItem.getAttachmentFile());
            if (file.exists()) {
                helper.addAttachment(file.getName(), file);
                logger.debug("Added attachment: {} for email to {}", file.getName(), mailItem.getEmail());
            } else {
                logger.warn("Attachment file not found: {} for email to {}", mailItem.getAttachmentFile(), mailItem.getEmail());
            }
        }
        
        try {
            mailSender.send(mimeMessage);
            logger.debug("Email successfully sent to: {}", mailItem.getEmail());
        } catch (Exception e) {
            logger.error("Failed to send email to: {}. Detailed error: {}", mailItem.getEmail(), e.getMessage());
            throw e;
        }
    }
}
