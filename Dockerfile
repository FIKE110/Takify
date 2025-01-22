#FROM eclipse-temurin:21
#
#WORKDIR /app
#ADD target/fortunechatbackendwithspring-0.0.1-SNAPSHOT.jar .
#EXPOSE 8080
#
#ENTRYPOINT ["java","-jar","fortunechatbackendwithspring-0.0.1-SNAPSHOT.jar"]
# Use Maven with Java 21 as the build stage
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy Maven files to cache dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# Copy source code to the container
COPY src ./src

# Build the application using Maven
RUN mvn clean package -DskipTests

# Use Java 21 runtime for the final image
FROM maven:3.8.8-eclipse-temurin-21-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
