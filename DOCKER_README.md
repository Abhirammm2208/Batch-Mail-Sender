# 🐳 Docker Setup for Bulk Mail Sender

## Quick Start

### Prerequisites
- Docker Desktop installed
- Docker Compose installed

### 🚀 One-Command Deployment

```bash
# Clone the repository
git clone https://github.com/Abhirammm2208/Batch-Mail-Sender.git
cd Batch-Mail-Sender

# Start the entire application stack
docker-compose up --build
```

The application will be available at: `http://localhost:8082`

## 📁 Docker Files Overview

- **Dockerfile**: Builds the Spring Boot application container
- **docker-compose.yml**: Orchestrates PostgreSQL + Spring Boot services
- **init.sql**: Database initialization script
- **.dockerignore**: Excludes unnecessary files from Docker build

## 🔧 Configuration

### Environment Variables (docker-compose.yml)

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://postgres:5432/Email_sender` | Database connection |
| `SPRING_DATASOURCE_USERNAME` | `postgres` | Database username |
| `SPRING_DATASOURCE_PASSWORD` | `Abhiram@123` | Database password |
| `MAIL_HOST` | `smtp.gmail.com` | SMTP server |
| `MAIL_PASSWORD` | `bckjamckbpfehrlj` | Email app password |

## 📋 Docker Commands

### Development Commands
```bash
# Build and start services
docker-compose up --build

# Start services in background
docker-compose up -d

# Stop services
docker-compose down

# View logs
docker-compose logs -f app
docker-compose logs -f postgres

# Rebuild only the app
docker-compose build app
```

### Production Commands
```bash
# Production deployment with restart policy
docker-compose -f docker-compose.yml up -d

# Scale the application (multiple instances)
docker-compose up --scale app=3

# Update application without downtime
docker-compose build app
docker-compose up -d --no-deps app
```

### Database Commands
```bash
# Connect to PostgreSQL container
docker exec -it email_sender_db psql -U postgres -d Email_sender

# Backup database
docker exec email_sender_db pg_dump -U postgres Email_sender > backup.sql

# Restore database
docker exec -i email_sender_db psql -U postgres Email_sender < backup.sql
```

## 🔍 Troubleshooting

### Common Issues

1. **Port Already in Use**
   ```bash
   # Check what's using port 8082
   netstat -ano | findstr :8082
   
   # Or change port in docker-compose.yml
   ports:
     - "8083:8082"  # Use port 8083 instead
   ```

2. **Database Connection Issues**
   ```bash
   # Check if PostgreSQL is healthy
   docker-compose ps
   
   # View database logs
   docker-compose logs postgres
   ```

3. **Email Authentication Issues**
   - Ensure Gmail App Password is correct
   - Check if 2FA is enabled on Gmail account
   - Verify SMTP settings in environment variables

### Health Checks
```bash
# Check application health
curl http://localhost:8082/actuator/health

# Check database connection
docker exec email_sender_db pg_isready -U postgres
```

## 📊 Monitoring & Logs

### Application Logs
```bash
# Follow application logs
docker-compose logs -f app

# Filter logs by level
docker-compose logs app | grep ERROR
```

### Database Logs
```bash
# PostgreSQL logs
docker-compose logs -f postgres
```

## 🚀 Production Deployment

### 1. Environment Setup
Create `.env` file:
```env
DB_PASSWORD=your_secure_password
MAIL_PASSWORD=your_app_password
POSTGRES_PASSWORD=your_secure_db_password
```

### 2. Production Docker Compose
```bash
# Use environment file
docker-compose --env-file .env up -d
```

### 3. Security Considerations
- Change default passwords
- Use Docker secrets for sensitive data
- Enable SSL/TLS for database connections
- Implement proper networking rules

## 🔄 CI/CD Integration

### GitHub Actions Example
```yaml
name: Deploy
on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Deploy to production
        run: |
          docker-compose pull
          docker-compose up -d --build
```

## 🏗️ Architecture Benefits

### Microservices Ready
- Easy to extract components into separate services
- Database can be shared or separated
- Independent scaling of components

### Development Benefits
- Consistent development environment
- Easy onboarding for new team members
- No local dependencies required

### Production Benefits
- Predictable deployments
- Easy rollbacks
- Container orchestration ready (Kubernetes)

## 📈 Scaling Options

### Horizontal Scaling
```bash
# Run multiple app instances
docker-compose up --scale app=3

# With load balancer (add nginx service to docker-compose)
docker-compose up --scale app=3 nginx
```

### Vertical Scaling
```yaml
# In docker-compose.yml
services:
  app:
    deploy:
      resources:
        limits:
          memory: 1G
          cpus: '0.5'
```