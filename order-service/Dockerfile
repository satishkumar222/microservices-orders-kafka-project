from openjdk:17-jdk-slim

workdir /app

copy target/*.jar app.jar

expose 1002

entrypoint ["java","-jar","app.jar"]