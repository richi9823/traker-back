FROM maven:3.9-eclipse-temurin-17 as builder
# Set the working directory in the container
WORKDIR /app
# Copy the pom.xml and the project files to the container
COPY pom.xml .
COPY src ./src
# Build the application using Maven
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

ARG JAR_FILE=/app/target/traker-0.0.1-SNAPSHOT.jar
COPY --from=builder ${JAR_FILE} .
EXPOSE 8080
CMD [ "sh", "-c", "java -jar traker-0.0.1-SNAPSHOT.jar" ]
