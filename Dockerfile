# Define a imagem base
FROM openjdk:17
WORKDIR /app
COPY target/empresa-api-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "empresa-api-0.0.1-SNAPSHOT.jar"]
