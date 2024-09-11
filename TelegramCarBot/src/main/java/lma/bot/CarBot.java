package lma.bot;

import jakarta.annotation.PostConstruct;
import lma.constants.BotHandlerConstants;
import lma.responseHandler.BotResponseHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

import static lma.constants.BotConstants.BOT_TOKEN;
import static lma.constants.BotConstants.BOT_USERNAME;
import static lma.constants.BotConstants.CALLBACK_HANDLING_COMMAND;
import static lma.constants.BotConstants.CREATOR_ID;
import static lma.constants.BotConstants.START_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.START_COMMAND_NAME;
import static lma.constants.BotConstants.SUBSCRIBE_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.SUBSCRIBE_COMMAND_NAME;
import static lma.constants.BotConstants.SUBSCRIPTIONS_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.SUBSCRIPTIONS_COMMAND_NAME;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND_NAME;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND_NAME;
import static lma.constants.BotHandlerConstants.CHAT_ID_FOR_EMPTY_MESSAGE;

@Component
public class CarBot extends AbilityBot {

    private final BotResponseHandler botResponseHandler;

    public CarBot(TelegramClient telegramClient, BotResponseHandler botResponseHandler) {
        super(telegramClient, BOT_USERNAME);
        this.botResponseHandler = botResponseHandler;
    }

    @Async
    public void sendMessage(SendMessage sendMessage) {
        if (!sendMessage.getChatId().equals(CHAT_ID_FOR_EMPTY_MESSAGE)) {
            silent.execute(sendMessage);
        }
    }

    public Ability startBot() {
        return Ability.builder()
                .name(START_COMMAND_NAME)
                .info(START_COMMAND_DESCRIPTION)
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> sendMessage(
                                botResponseHandler.handleStartCommand(ctx.chatId(), ctx.user().getId())
                        )
                )
                .build();
    }

    public Ability subscribe() {
        return Ability.builder()
                .name(SUBSCRIBE_COMMAND_NAME)
                .info(SUBSCRIBE_COMMAND_DESCRIPTION)
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> sendMessage(
                                botResponseHandler.handleSubscribeCommand(ctx.chatId(), ctx.user().getId())
                        )
                )
                .build();
    }

    public Ability unsubscribe() {
        return Ability.builder()
                .name(UNSUBSCRIBE_COMMAND_NAME)
                .info(UNSUBSCRIBE_COMMAND_DESCRIPTION)
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> sendMessage(
                                botResponseHandler.handleUnsubscribeCommand(ctx.chatId(), ctx.user().getId())
                        )
                )
                .build();
    }

    public Ability subscriptions() {
        return Ability.builder()
                .name(SUBSCRIPTIONS_COMMAND_NAME)
                .info(SUBSCRIPTIONS_COMMAND_DESCRIPTION)
                .input(0)
                .locality(Locality.USER)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> sendMessage(
                                botResponseHandler.handleSubscriptionsCommand(ctx.chatId(), ctx.user().getId())
                        )
                )
                .build();
    }

    public Ability unsubscribeAll() {
        return Ability.builder()
                .name(UNSUBSCRIBE_ALL_COMMAND_NAME)
                .info(UNSUBSCRIBE_ALL_COMMAND_DESCRIPTION)
                .input(0)
                .locality(Locality.ALL)
                .privacy(Privacy.PUBLIC)
                .action(ctx -> sendMessage(
                                botResponseHandler.handleUnsubscribeAllCommand(ctx.chatId(), ctx.user().getId())
                        )
                )
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

    @Override
    @Async("threadPollTaskExecutor")
    public void consume(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                sendMessage(botResponseHandler.handleCallbacks(update, silent));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            super.consume(update);
        }
    }

    @Override
    @PostConstruct
    public void onRegister() {
        super.onRegister();
    }
}
