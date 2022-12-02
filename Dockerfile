FROM gradle:7-jdk11 AS build

WORKDIR /home/gradle/src
COPY --chown=gradle:gradle . .
RUN gradle buildFatJar --no-daemon

FROM openjdk:11

WORKDIR /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/service.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/service.jar"]