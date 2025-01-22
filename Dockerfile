#FROM eclipse-temurin:21
#
#WORKDIR /app
#ADD target/fortunechatbackendwithspring-0.0.1-SNAPSHOT.jar .
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","fortunechatbackendwithspring-0.0.1-SNAPSHOT.jar"]

FROM maven:3.9.4-eclipse-temurin-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Maven's settings to cache dependencies
COPY pom.xml ./
COPY src ./src

# Install dependencies and build the project
RUN mvn clean package -DskipTests

# Use a lightweight JDK runtime for the final image
FROM openjdk:17-jdk-slim

# Set the working directory for the app
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application's port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
