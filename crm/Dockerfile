FROM eclipse-temurin:21-jdk AS builder

WORKDIR /build

COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY src src
RUN ./gradlew build -x test --no-daemon


FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=builder /build/build/libs/*.jar project.jar

EXPOSE 9000
ENTRYPOINT ["java", "-jar", "project.jar"]