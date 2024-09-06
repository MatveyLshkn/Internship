package lma.runner;

import lma.bot.CarBot;
import lma.parser.PageParser;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import static lma.constants.BotConstants.BOT_TOKEN;

@Component
@RequiredArgsConstructor
public class AppRunner implements CommandLineRunner {

    private final CarBot carBot;

    @Override
    public void run(String... args) throws Exception {

        TelegramBotsLongPollingApplication telegramBotsLongPollingApplication = new TelegramBotsLongPollingApplication();
        telegramBotsLongPollingApplication.registerBot(BOT_TOKEN, carBot);
    }
}
