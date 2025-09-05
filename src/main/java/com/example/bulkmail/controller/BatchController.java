package com.example.bulkmail.controller;

import com.example.bulkmail.repo.MailBatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BatchController {
    @Autowired
    private MailBatchRepository mailBatchRepository;

    @GetMapping("/batches")
    public String getBatches(Model model) {
        model.addAttribute("batches", mailBatchRepository.findAll());
        return "batch";
    }
}
