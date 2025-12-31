FROM eclipse-temurin:21.0.8_9-jdk-jammy AS builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw clean install -DskipTests

FROM eclipse-temurin:21.0.8_9-jre-jammy AS final
WORKDIR /opt/app
EXPOSE 8081

# Environment variables with default values
ENV DB_USERNAME=admin
ENV DB_ROOT_PASSWORD=admin
ENV MONGODB_HOST=localhost
ENV MONGODB_PORT=27017
ENV INVENTORY_HOST=localhost
ENV INVENTORY_PORT=8082
ENV SERVER_PORT=8081

COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]