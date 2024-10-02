FROM maven:3.9.4-eclipse-temurin-21-alpine AS build
WORKDIR /app
COPY welltalk /app
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk-slim
COPY --from=build /app/target/*.jar welltalk.jar 
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "welltalk.jar"]
