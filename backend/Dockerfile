
# Stage 1: Build and compile
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
COPY pom.xml /app
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn -f /app/pom.xml clean package -DskipTests

# Satge 2: Run
FROM openjdk:17
WORKDIR /app
COPY --from=build /app/target/myshop-0.3.0.jar ./app.jar

EXPOSE 8081
EXPOSE 8082
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]


