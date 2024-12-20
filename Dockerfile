FROM openjdk:21-jdk
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
COPY application.properties application.properties
ENTRYPOINT ["java","-Dspring.config.location=application.properties","-jar","/app.jar"]