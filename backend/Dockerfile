FROM maven:3.9-eclipse-temurin-17 AS test
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn test

FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jre-alpine

# opencv
RUN sudo apt-get update
RUN sudo apt-get install -y libstdc++6
RUN rm -rf /var/lib/apt/lists/*
RUN apk add --no-cache libstdc++
RUN ldd /path/to/libopencv_java470.so

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
