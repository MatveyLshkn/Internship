FROM amazoncorretto:21-alpine

COPY build/resources/main/application.properties application.properties
COPY build/libs/task4-1.0-SNAPSHOT.jar task4-1.0-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "task4-1.0-SNAPSHOT.jar"]
EXPOSE 8080