FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE 1003

ENTRYPOINT ["java","-jar","app.jar"]