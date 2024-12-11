# Base image
FROM openjdk:17-jdk-alpine

# Add a volume pointing to /tmp
VOLUME /tmp

# Copy the application JAR file
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar

# Run the JAR file
ENTRYPOINT ["java","-jar","/app.jar"]
