# Batch-Mail-Sender

A Spring Boot application for sending bulk emails using Excel files, PostgreSQL, and email templates.

## Mail Configuration Issues Fixed

### Key Problems Resolved:
1. **Removed hardcoded credentials** - Configuration now properly uses Spring Boot auto-configuration
2. **Added sender email** - Emails now include proper "from" address
3. **Improved error logging** - Better debugging information for mail failures
4. **Gmail App Password support** - Updated documentation for proper Gmail authentication

### Gmail Setup (Required for Email Sending)

**IMPORTANT**: Gmail requires App Passwords for third-party applications since 2022.

#### Steps to set up Gmail:
1. Enable 2-Factor Authentication on your Gmail account
2. Generate an App Password:
   - Go to Google Account settings
   - Security → 2-Step Verification → App passwords
   - Generate a new app password for "Mail"
3. Set environment variables:
   ```bash
   export MAIL_PASSWORD="your_16_character_app_password"
   export MAIL_FROM="your_email@gmail.com"
   ```

### Environment Variables

Set these environment variables before running the application:

```bash
# Database
export DB_PASSWORD="your_database_password"

# Email (Gmail)
export MAIL_HOST="smtp.gmail.com"
export MAIL_PASSWORD="your_gmail_app_password"  # Must be App Password, not regular password
export MAIL_FROM="your_email@gmail.com"
```

### Common Issues and Solutions

1. **Authentication failed**: 
   - Ensure you're using a Gmail App Password, not your regular password
   - Verify 2FA is enabled on your Gmail account

2. **No sender address**: 
   - Set the `MAIL_FROM` environment variable
   - Verify the sender email matches your Gmail account

3. **Connection issues**:
   - Check that SMTP settings are correct (smtp.gmail.com:587)
   - Verify TLS is enabled

### Running the Application

```bash
# Set required environment variables
export MAIL_PASSWORD="your_gmail_app_password"
export MAIL_FROM="your_email@gmail.com"
export DB_PASSWORD="your_db_password"

# Run the application
mvn spring-boot:run
```

The application will be available at `http://localhost:8081`
