FROM eclipse-temurin:21

WORKDIR /app
ADD target/fortunechatbackendwithspring-0.0.1-SNAPSHOT.jar .
EXPOSE 8080

ENTRYPOINT ["java","-jar","fortunechatbackendwithspring-0.0.1-SNAPSHOT.jar"]