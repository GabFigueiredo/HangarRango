# Stage 1: Build
FROM ubuntu:latest AS build


RUN apt-get update
RUN apt-get install open-jdk-23 -y

COPY . .

RUN apt-get install maven -y
RUN mvn clean install -Dskiptests

# Stage 2: Runtime
FROM open-jdk-23-slim

EXPOSE 8080

COPY --from=build /app/target/cantina-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
