FROM amazoncorretto:21 AS build
WORKDIR /app
COPY . .
RUN ./gradlew clean build -x test

FROM amazoncorretto:21
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080:8080
ENTRYPOINT ["java", "-jar", "app.jar"]
