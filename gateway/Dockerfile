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


ENV GATEWAY_PORT=9000
ENV KEYCLOAK_HOST=localhost
ENV KEYCLOAK_PORT=8080
ENV KEYCLOAK_REALM=devops-micro

ENV PRODUCT_SERVICE_HOST=localhost
ENV PRODUCT_SERVICE_PORT=8080

ENV ORDER_SERVICE_HOST=localhost
ENV ORDER_SERVICE_PORT=8081

ENV INVENTORY_SERVICE_HOST=localhost
ENV INVENTORY_SERVICE_PORT=8082

COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar"]