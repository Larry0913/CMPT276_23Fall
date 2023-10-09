FROM maven:3.8.5-openjdk-18 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:18
COPY --from=build /target/demo-0.0.1-SNAPSHOT.jar demo.jar
EXPOSE 8080
ENTRYPOINT [ "java","-jar","demo.jar"]