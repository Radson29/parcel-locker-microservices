FROM openjdk:17
WORKDIR /app
COPY target/courier-service.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]