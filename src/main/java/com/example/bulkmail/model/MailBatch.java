package com.example.bulkmail.model;

import lombok.Data;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Data
public class MailBatch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "text")
    private String status; // e.g., PENDING, SENDING, SENT, FAILED
    @Column(columnDefinition = "text")
    private String errorMessage;

    @OneToMany(mappedBy = "mailBatch", cascade = CascadeType.ALL)
    private List<MailItem> mailItems;

    @Lob
    private byte[] excelFile;
}
