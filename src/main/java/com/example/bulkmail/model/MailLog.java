package com.example.bulkmail.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class MailLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    @Column(columnDefinition = "text")
    private String details; // JSON or plain text of what was sent

    private Long batchId;
}
