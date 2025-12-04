# Use Eclipse Temurin JDK 17 as base image
FROM eclipse-temurin:17-jdk-jammy

# Set working directory in container
WORKDIR /app

# Copy Maven wrapper and pom.xml first for better layer caching
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn .mvn
COPY pom.xml .

# Download dependencies (this layer will be cached if pom.xml doesn't change)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Build the application
RUN ./mvnw clean package -DskipTests

# Expose the port your app runs on
EXPOSE 8082

# Set environment variables with default values
ENV DB_PASSWORD=Abhiram@123
ENV MAIL_PASSWORD=bckjamckbpfehrlj
ENV SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/Email_sender

# Run the application
CMD ["java", "-jar", "target/EmailSender-0.0.1-SNAPSHOT.jar"]