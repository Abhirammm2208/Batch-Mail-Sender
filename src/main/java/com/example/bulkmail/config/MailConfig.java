package com.example.bulkmail.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class MailConfig {
    // Spring Boot auto-configuration handles JavaMailSender bean creation
    // using properties from application.properties
    // This allows for proper environment variable usage and avoids hardcoded credentials
}
