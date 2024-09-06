package lma.bot;

import lma.constants.BotConstants;
import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.User;
import lma.repository.BrandRepository;
import lma.repository.ModelRepository;
import lma.repository.UserRepository;
import lma.responseHandler.BotResponseHandler;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.Constants;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.abilitybots.api.toggle.BareboneToggle;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.ArrayList;
import java.util.List;

import static lma.constants.BotConstants.BOT_TOKEN;
import static lma.constants.BotConstants.BOT_USERNAME;
import static lma.constants.BotConstants.COMMAND_FOR_HANDLING_CALLBACK;
import static lma.constants.BotConstants.CREATOR_ID;
import static lma.constants.BotConstants.START_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.START_COMMAND_NAME;
import static lma.constants.BotConstants.SUBSCRIBE_COMMAND_NAME;
import static lma.constants.BotConstants.SUBSCRIPTIONS_COMMAND_NAME;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND_NAME;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND_NAME;

@Component
public class CarBot extends AbilityBot {

    private final BotResponseHandler botResponseHandler;

    public CarBot(TelegramClient telegramClient, BotResponseHandler botResponseHandler) {
        super(telegramClient, BOT_USERNAME);
        this.botResponseHandler = botResponseHandler;
    }

    public Ability startBot() {
        return Ability.builder()
                .name(START_COMMAND_NAME)
                .info(START_COMMAND_DESCRIPTION)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .post(ctx -> botResponseHandler.handleStartCommand(ctx.chatId(), ctx.user().getId()))
                .build();
    }

    public Ability subscribe() {
        return Ability.builder()
                .name(SUBSCRIBE_COMMAND_NAME)
                .info(START_COMMAND_DESCRIPTION)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .post(cxt -> botResponseHandler.sendInlineKeyboardBrands(cxt.chatId()))
                .build();
    }

    public Ability processCallback() {
        return Ability.builder()
                .name(COMMAND_FOR_HANDLING_CALLBACK)
                .post(ctx -> botResponseHandler.handleCallbacks(ctx.update()))
                .build();
    }

    public Ability unsubscribe() {
        return Ability.builder()
                .name(UNSUBSCRIBE_COMMAND_NAME)
                .info(UNSUBSCRIBE_COMMAND_DESCRIPTION)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .post(ctx -> botResponseHandler.handleUnsubscribe(ctx.chatId(), ctx.user().getId()))
                .build();
    }

    public Ability subscriptions() {
        return Ability.builder()
                .name(SUBSCRIPTIONS_COMMAND_NAME)
                .info(START_COMMAND_DESCRIPTION)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .post(ctx -> botResponseHandler.getSubscriptionList(ctx.chatId(), ctx.user().getId()))
                .build();
    }

    public Ability unsubscribeAll() {
        return Ability.builder()
                .name(UNSUBSCRIBE_ALL_COMMAND_NAME)
                .info(UNSUBSCRIBE_ALL_COMMAND_DESCRIPTION)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .post(ctx -> botResponseHandler.unsubscribeAllModels(ctx.chatId(), ctx.user().getId()))
                .build();
    }

    @Override
    public long creatorId() {
        return CREATOR_ID;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    public String getBotToken() {
        return BOT_TOKEN;
    }
}
