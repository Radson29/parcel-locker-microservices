# Używamy oficjalnego obrazu Javy
FROM openjdk:17-jdk-slim

# Katalog roboczy w kontenerze
WORKDIR /app

# Kopiujemy zbudowany plik JAR do obrazu
COPY target/eureka-server.jar app.jar

# Komenda uruchamiająca aplikację
ENTRYPOINT ["java", "-jar", "app.jar"]
