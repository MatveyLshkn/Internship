FROM amazoncorretto:21-alpine

COPY build/resources resources/
COPY build/libs/security-0.1-plain.jar security-0.1-plain.jar

ENTRYPOINT ["java", "-jar", "security-0.1-plain.jar"]
EXPOSE 8080