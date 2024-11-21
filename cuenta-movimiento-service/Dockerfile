FROM openjdk:21-jdk-slim
EXPOSE 8180
COPY build/libs/cuenta-movimiento-service-0.0.1-SNAPSHOT.jar cuenta-movimiento-service.jar
ENTRYPOINT ["java", "-jar", "/cuenta-movimiento-service.jar"]