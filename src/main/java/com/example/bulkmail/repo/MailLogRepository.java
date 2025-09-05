package com.example.bulkmail.repo;

import com.example.bulkmail.model.MailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailLogRepository extends JpaRepository<MailLog, Long> {
}
