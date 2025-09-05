package com.example.bulkmail.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MailItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    @Column(columnDefinition = "text")
    private String subject;
    @Column(columnDefinition = "text")
    private String message;
    @Column(columnDefinition = "text")
    private String attachmentFile;
    private String status; // e.g., PENDING, SENDING, SENT, FAILED
    @Column(columnDefinition = "text")
    private String errorMessage;

    @ManyToOne
    @JoinColumn(name = "mail_batch_id")
    private MailBatch mailBatch;
}
