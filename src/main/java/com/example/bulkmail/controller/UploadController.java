package com.example.bulkmail.controller;

import com.example.bulkmail.model.MailBatch;
import com.example.bulkmail.service.ExcelService;
import com.example.bulkmail.service.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class UploadController {
    @Autowired
    private ExcelService excelService;

    @Autowired
    private MailSenderService mailSenderService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "attachments", required = false) MultipartFile[] attachments,
                              Model model) {
        try {
            MailBatch mailBatch = excelService.readExcel(file);

            // Save attachments to disk and associate with MailItems if filenames match
            if (attachments != null && attachments.length > 0) {
                for (MultipartFile attachment : attachments) {
                    String originalFilename = attachment.getOriginalFilename();
                    if (originalFilename != null && !originalFilename.isEmpty()) {
                        java.io.File dest = new java.io.File("uploads/" + originalFilename);
                        dest.getParentFile().mkdirs();
                        attachment.transferTo(dest);
                        // Associate with MailItem if the Excel has the same filename
                        for (var mailItem : mailBatch.getMailItems()) {
                            if (originalFilename.equals(mailItem.getAttachmentFile())) {
                                mailItem.setAttachmentFile(dest.getAbsolutePath());
                            }
                        }
                    }
                }
            }

            mailSenderService.sendBulkEmails(mailBatch);
            model.addAttribute("message", "Emails are being sent!");
        } catch (IOException e) {
            model.addAttribute("message", "Error reading the file: " + e.getMessage());
        }
        return "index";
    }
}
