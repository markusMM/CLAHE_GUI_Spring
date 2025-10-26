# backend/Dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app

# Copy the built JAR file into the container
COPY target/clahe-backend-1.0.0.jar app.jar

# Run it
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
EXPOSE 8080
