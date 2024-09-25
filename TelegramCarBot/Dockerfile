FROM amazoncorretto:17-alpine

COPY build/libs/TelegramCarBot-1.jar bot-1.jar

ENTRYPOINT ["java", "-jar", "bot-1.jar"]
EXPOSE 8080