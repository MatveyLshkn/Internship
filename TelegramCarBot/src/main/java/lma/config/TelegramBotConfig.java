package lma.config;

import lma.bot.CarBot;
import lma.responseHandler.BotResponseHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static lma.constants.BotConstants.BOT_TOKEN;

@Configuration
public class TelegramBotConfig {

    @Bean
    public TelegramClient telegramClient() throws TelegramApiException {
        return new OkHttpTelegramClient(BOT_TOKEN);
    }

    @Bean
    public CarBot carBot(TelegramClient telegramClient, BotResponseHandler botResponseHandler) {

        return new CarBot(telegramClient, botResponseHandler);
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(CarBot carBot) throws TelegramApiException {

        TelegramBotsLongPollingApplication telegramBotsLongPollingApplication = new TelegramBotsLongPollingApplication();
        telegramBotsLongPollingApplication.registerBot(BOT_TOKEN, carBot);

        return telegramBotsLongPollingApplication;
    }
}
