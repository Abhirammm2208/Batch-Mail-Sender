package com.example.bulkmail.service;

import com.example.bulkmail.model.MailBatch;
import com.example.bulkmail.model.MailItem;
import com.example.bulkmail.repo.MailBatchRepository;
import com.example.bulkmail.repo.MailItemRepository;
import com.example.bulkmail.repo.MailLogRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailSenderServiceTest {

    @Mock
    private JavaMailSender mailSender;

    @Mock
    private MailBatchRepository mailBatchRepository;

    @Mock
    private MailItemRepository mailItemRepository;

    @Mock
    private MailLogRepository mailLogRepository;

    @InjectMocks
    private MailSenderService mailSenderService;

    @Test
    void testSendBulkEmailsConfiguration() {
        // Given
        ReflectionTestUtils.setField(mailSenderService, "fromEmail", "test@example.com");
        
        MailBatch mailBatch = new MailBatch();
        mailBatch.setId(1L);
        
        MailItem mailItem = new MailItem();
        mailItem.setEmail("recipient@example.com");
        mailItem.setSubject("Test Subject");
        mailItem.setMessage("Test Message");
        
        mailBatch.setMailItems(Arrays.asList(mailItem));

        when(mailBatchRepository.save(any(MailBatch.class))).thenReturn(mailBatch);
        when(mailItemRepository.save(any(MailItem.class))).thenReturn(mailItem);

        // When
        mailSenderService.sendBulkEmails(mailBatch);

        // Then
        verify(mailBatchRepository, times(2)).save(any(MailBatch.class));
        verify(mailItemRepository, times(1)).save(any(MailItem.class));
        verify(mailLogRepository, times(1)).save(any());
    }
}