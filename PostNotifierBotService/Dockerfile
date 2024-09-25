FROM amazoncorretto:17-alpine

COPY build/libs/PostNotifierBotService-1.jar notifier-1.jar

ENTRYPOINT ["java", "-jar", "notifier-1.jar"]
EXPOSE 8080