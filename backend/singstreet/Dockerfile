# Fetch base image for Java
FROM openjdk:11-jdk-alpine

# Maintainer information (optional)
LABEL maintainer="example@example.com"

# Set volume point to /tmp
VOLUME /tmp

# Copy your application jar file to the docker image
ARG JAR_FILE=build/libs/singstreet-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

# Execute the application
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]