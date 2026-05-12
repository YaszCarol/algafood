FROM eclipse-temurin:17-alpine AS build

WORKDIR /workspace/app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x mvnw \
    && ./mvnw -B -DskipTests clean package

FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /workspace/app/target/algafood-api-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
