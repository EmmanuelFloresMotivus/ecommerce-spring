#-----ETAPA 1: BUILD (Construccion)-----
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTest

#-----ETAPA 2: RUNTIME (Producto Final)-----
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=builder app/target/*.jar product.jar
####puede funcionar sin exponer el puerto pero por temas de documentacion se pone
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "product.jar"]