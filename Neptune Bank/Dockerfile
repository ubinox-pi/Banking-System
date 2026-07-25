FROM gradle:8.8-jdk21 AS builder
WORKDIR /app

COPY . .
RUN ./gradlew clean bootJar --no-daemon

FROM amazoncorretto:21-alpine
LABEL authors="Ashish Kushwaha"

WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
