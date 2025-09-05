package com.example.bulkmail.repo;

import com.example.bulkmail.model.MailBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailBatchRepository extends JpaRepository<MailBatch, Long> {
}
