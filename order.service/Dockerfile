FROM eclipse-temurin:21.0.8_9-jdk-jammy AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21.0.8_9-jre-jammy AS final
WORKDIR /opt/app
EXPOSE 8081

# Database Configuration
ENV DB_HOST=localhost
ENV DB_PORT=3306
ENV DB_NAME=order-service
ENV DB_USERNAME=root
ENV DB_PASSWORD=root

# Server Configuration
ENV SERVER_PORT=8081

# Inventory Service Configuration
ENV INVENTORY_HOST=localhost
ENV INVENTORY_PORT=8082

# Kafka Configuration
ENV KAFKA_BOOTSTRAP_SERVERS=localhost:9092
ENV KAFKA_DEFAULT_TOPIC=order-placed

COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]