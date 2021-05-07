FROM gradle:latest AS build-stage

COPY . /build

WORKDIR /build

RUN gradle :backend:clean :backend:build --no-daemon

FROM openjdk:8-jre-alpine AS production-stage

COPY --from=build-stage /build/backend/build/libs/app.jar /app/app.jar

WORKDIR /app

EXPOSE 8080

ENTRYPOINT ["java", "-server", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:InitialRAMFraction=2", "-XX:MinRAMFraction=2", "-XX:MaxRAMFraction=2", "-XX:+UseG1GC", "-XX:MaxGCPauseMillis=100", "-XX:+UseStringDeduplication", "-jar", "app.jar"]