# build stage

FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvnw clean package -DskipTests


# runtime stage

FROM eclipse-temurin:17-jre
WORKDIR /app

#copy jar from build stage

COPY --from=build /app/target/*.jar app.jar

EXPOSE 9095

# run the application

ENTRYPOINT [ "java", "-jar", "app.jar" ]