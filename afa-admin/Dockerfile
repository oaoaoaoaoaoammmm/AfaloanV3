FROM gradle:8.8 as build

WORKDIR /workspace

COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts

RUN gradle clean build -x test

FROM amazoncorretto:21-alpine

WORKDIR /app

COPY --from=build /workspace/build/libs/afa-admin-2.0.0.jar ./afa-admin.jar

ENTRYPOINT ["java", "-jar", "afa-admin.jar"]