FROM openjdk:21-jdk-slim
EXPOSE 8080
COPY build/libs/cliente-persona-service-0.0.1-SNAPSHOT.jar cliente-persona-service.jar
ENTRYPOINT ["java", "-jar", "/cliente-persona-service.jar"]