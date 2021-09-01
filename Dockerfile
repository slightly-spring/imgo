# Dockerfile
# for spring boot .jar 
# JDK: 11
FROM openjdk:11
WORKDIR /
ADD build/libs/*.jar app.jar
EXPOSE 8080
CMD java -jar app.jar
