FROM gradle:8.8 as build

WORKDIR /workspace

COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts

RUN gradle clean build -x test -x detekt -x koverVerify

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /workspace/build/libs/afa-notification-3.0.0.jar ./afa-notification.jar

ENTRYPOINT ["java", "-jar", "afa-notification.jar"]