package lma;

import lma.bot.CarBot;
import lma.constants.BotConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import java.io.IOException;

import static lma.constants.BotConstants.BOT_TOKEN;


@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class TelegramCarBotApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(TelegramCarBotApplication.class, args);
    }
}
