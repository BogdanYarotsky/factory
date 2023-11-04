FROM maven:3.9-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B dependency:go-offline
COPY src src
RUN mvn -B package

FROM eclipse-temurin:17-jre-jammy
COPY --from=build /app/target/api-0.0.1-SNAPSHOT.jar /usr/local/lib/api.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/api.jar"]

