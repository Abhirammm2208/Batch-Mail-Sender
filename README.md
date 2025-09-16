# 📧 Batch-Mail-Sender

A **Spring Boot application** for sending bulk emails with attachments using **Gmail SMTP** and tracking their status in a **PostgreSQL database**.  

---

## ✨ Features  
- ✉️ **Efficient Batch Sending:** Send emails to a large list of recipients with optimized performance.  
- 📎 **Attachments Support:** Easily attach files to outgoing emails.  
- ⚙️ **Customizable SMTP Settings:** Configure mail server, authentication, and security protocols.  
- 📄 **Template Support:** Use HTML templates for rich, professional-looking messages.  
- 🚀 **Simple Command-Line Interface:** Run bulk email jobs directly from your terminal.  
- 🔒 **Secure Credential Handling:** Manage sensitive credentials safely with environment variables.  
- 🗄️ **Database Integration:** Store email batches, recipients, and sending status in PostgreSQL.  
- 🔁 **Retry & Error Logging:** Automatic retries and failure logs for robust operation.  

---

## 🛠️ Prerequisites  
- ☕ Java 17+  
- 🛠️ Maven (for build & dependency management)  
- 🐘 PostgreSQL database  
- 📧 Gmail account with **App Password**  
- 🌐 Outbound access to `smtp.gmail.com` on port **587**  

---

## ⚙️ Configuration  

Edit the `application.properties` file:  

server.port=8081
### 🗄️ Database Configuration  
- 🌐 **URL:** `jdbc:postgresql://localhost:5432/Email_sender`  
- 👤 **Username:** `postgres`  
- 🔑 **Password:** `${DB_PASSWORD:your_db_password_here}`  
- 🔄 **DDL Auto:** `update` (auto schema update)  
- 📝 **Show SQL:** `true` (prints executed queries in logs)  

### ✉️ Mail Configuration  
- 📡 **Host:** `smtp.gmail.com`  
- 🔌 **Port:** `587` (STARTTLS)  
- 👤 **Username:** `your_gmail_address@gmail.com`  
- 🔑 **Password:** `${MAIL_PASSWORD:your_app_password}`  
- 🔒 **SMTP Auth:** `true`  
- 🔄 **STARTTLS Enable:** `true`  
- ✅ **STARTTLS Required:** `true`  
- 🐞 **Debug Mode:** `true` (logs SMTP communication for troubleshooting) 


🔑 **Important:**  
- Use an **App Password** from Google (16-character code without spaces).  
- Do **not** use your normal Gmail password if 2FA is enabled.  

---

## ▶️ Running the Application  

Open your terminal and run:  

1. Clean and install dependencies
mvn clean install

2. Start the Spring Boot application
mvn spring-boot:run


By default, the service will be running on:  
👉 [http://localhost:8081](http://localhost:8081)  

---

## 🧪 How It Works  
1. 📥 Load a batch of email items (recipients, subject, body, attachments).  
2. 🗄️ Persist them in PostgreSQL.  
3. 🔗 Connect to Gmail SMTP with STARTTLS.  
4. 📤 Send emails in bulk.  
5. ✅ Update DB with status (**SUCCESS** / **FAILED**).  
6. 📝 Errors are logged into `mail_log` for review.  

---

## 🛑 Troubleshooting  

| Issue                | Possible Cause                                        | Solution                                                      |
| -------------------- | ----------------------------------------------------- | ------------------------------------------------------------- |
| `AUTH LOGIN failed`  | Using normal Gmail password / invalid app password     | Generate a new Gmail **App Password**                         |
| No STARTTLS in logs  | Missing config                                        | Set: `spring.mail.properties.mail.smtp.starttls.required=true` |
| Port 25 doesn’t work | Gmail blocks port 25 for SMTP                         | Use **587 (STARTTLS)** or **465 (SSL)**                       |
| Connection blocked   | Firewall or network restricts SMTP traffic            | Allow outbound port **587** to `smtp.gmail.com`               |

---

