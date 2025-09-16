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

    @Enumerated(EnumType.STRING)
    private MailStatus status;

    @Column(columnDefinition = "text")
    private String errorMessage;

    @OneToMany(mappedBy = "mailBatch", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MailItem> mailItems;

    @Lob
    private byte[] excelFile;
}
