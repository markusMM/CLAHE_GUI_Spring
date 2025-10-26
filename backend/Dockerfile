# backend/Dockerfile
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY backend/target/myapp.jar myapp.jar
ENTRYPOINT ["java", "-jar", "myapp.jar"]
EXPOSE 8080
