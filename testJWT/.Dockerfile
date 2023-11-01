#FROM adoptopenjdk:17-jre-hotspot
FROM openjdk:17-jdk-slim

#COPY target/ventas-0.0.1-SNAPSHOT.jar /app.jar
COPY target/testJWT-0.0.1-SNAPSHOT.jar /app.jar

EXPOSE 8080

CMD ["java","-jar","/app.jar"]

