package com.example.bulkmail.repo;

import com.example.bulkmail.model.MailItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailItemRepository extends JpaRepository<MailItem, Long> {
}
