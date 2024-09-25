package lma.bot;

import jakarta.annotation.PostConstruct;
import lma.constants.BotHandlerConstants;
import lma.exception.TelegramBotException;
import lma.responseHandler.BotResponseHandler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.MessageContext;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.groupadministration.SetChatPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lma.constants.BotConstants.BOT_TOKEN;
import static lma.constants.BotConstants.BOT_USERNAME;
import static lma.constants.BotConstants.COMMANDS_COMMAND;
import static lma.constants.BotConstants.COMMANDS_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.CREATOR_ID;
import static lma.constants.BotConstants.START_COMMAND;
import static lma.constants.BotConstants.START_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.START_COMMAND_NAME;
import static lma.constants.BotConstants.SUBSCRIBE_COMMAND;
import static lma.constants.BotConstants.SUBSCRIBE_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.SUBSCRIBE_COMMAND_NAME;
import static lma.constants.BotConstants.SUBSCRIPTIONS_COMMAND;
import static lma.constants.BotConstants.SUBSCRIPTIONS_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.SUBSCRIPTIONS_COMMAND_NAME;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.UNSUBSCRIBE_ALL_COMMAND_NAME;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND_DESCRIPTION;
import static lma.constants.BotConstants.UNSUBSCRIBE_COMMAND_NAME;
import static lma.constants.BotHandlerConstants.CHAT_ID_FOR_EMPTY_MESSAGE;
import static lma.constants.CommonConstants.TELEGRAM_BOT_EXCEPTION_MESSAGE;
import static lma.constants.CommonConstants.THREAD_POLL_TASK_EXECUTOR_NAME;

@Component
public class CarBot extends AbilityBot {

    private final BotResponseHandler botResponseHandler;

    public CarBot(TelegramClient telegramClient, BotResponseHandler botResponseHandler) {
        super(telegramClient, BOT_USERNAME);
        this.botResponseHandler = botResponseHandler;
    }

    //@Async(THREAD_POLL_TASK_EXECUTOR_NAME)?????
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

    @Override
    @Async(THREAD_POLL_TASK_EXECUTOR_NAME)
    public void consume(Update update) {
        if (update.hasCallbackQuery()) {
            try {
                sendMessage(botResponseHandler.handleCallbacks(update, silent));
            } catch (InterruptedException | IOException e) {
                throw new TelegramBotException(TELEGRAM_BOT_EXCEPTION_MESSAGE, e);
            }
        } else {
            super.consume(update);
        }
    }

    private void initMyCommands() {
        List<BotCommand> commands = new ArrayList<>();

        commands.add(new BotCommand(START_COMMAND, START_COMMAND_DESCRIPTION));
        commands.add(new BotCommand(COMMANDS_COMMAND, COMMANDS_COMMAND_DESCRIPTION));
        commands.add(new BotCommand(SUBSCRIBE_COMMAND, SUBSCRIBE_COMMAND_DESCRIPTION));
        commands.add(new BotCommand(UNSUBSCRIBE_COMMAND, UNSUBSCRIBE_COMMAND_DESCRIPTION));
        commands.add(new BotCommand(UNSUBSCRIBE_ALL_COMMAND, UNSUBSCRIBE_ALL_COMMAND_DESCRIPTION));
        commands.add(new BotCommand(SUBSCRIPTIONS_COMMAND, SUBSCRIPTIONS_COMMAND_DESCRIPTION));

        silent.execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
    }

    @Override
    @PostConstruct
    public void onRegister() {
        initMyCommands();
        super.onRegister();
    }
}
