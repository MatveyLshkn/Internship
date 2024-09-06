package lma.config;

import lma.bot.CarBot;
import lma.repository.BrandRepository;
import lma.repository.ModelRepository;
import lma.repository.UserRepository;
import lma.responseHandler.BotResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramBotConfig {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Bean
    public TelegramClient telegramClient() throws TelegramApiException {
        return new OkHttpTelegramClient(botToken);
    }

    @Bean
    public CarBot myTelegramAbilityBot(TelegramClient telegramClient,
                                       BotResponseHandler botResponseHandler) {

        return new CarBot(telegramClient, botResponseHandler);
    }
}
