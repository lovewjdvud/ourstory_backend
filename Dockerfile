# 공식 OpenJDK 런타임 이미지를 사용합니다
FROM openjdk:17.0.2-jdk-slim-buster AS buider

WORKDIR /app
COPY gradlew build.gradle.kts settings.gradle.kts ./
COPY gradle ./gradle
COPY src/main ./src/main

RUN chmod +x ./gradlew

RUN ./gradlew bootJar

FROM openjdk:17.0.2-jdk-slim-buster

WORKDIR /app
COPY --from=buider /app/build/libs/ourstory-*.jar app.jar

ENV PROFILE ="dev"

ENTRYPOINT java -jar app.jar --spring.profiles.active=$PROFILE

