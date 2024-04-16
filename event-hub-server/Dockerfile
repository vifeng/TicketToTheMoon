FROM eclipse-temurin:17-jdk-alpine AS builder
WORKDIR /app
COPY ./build/libs/*.jar eventhubserver.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "eventhubserver.jar"]


# Now copy it into our base image.
FROM gcr.io/distroless/java17-debian12
WORKDIR /app
COPY --from=builder /app/eventhubserver.jar .
ENTRYPOINT ["java", "-jar", "eventhubserver.jar"]
