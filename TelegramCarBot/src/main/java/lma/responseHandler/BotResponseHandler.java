package lma.responseHandler;

import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.ModelCheck;
import lma.entity.User;
import lma.parser.PageParser;
import lma.service.BrandService;
import lma.service.ModelCheckService;
import lma.service.ModelService;
import lma.service.PostService;
import lma.service.ScheduledService;
import lma.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static lma.constants.BotHandlerConstants.ALREADY_SUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.BRANDS_MESSAGE_COLUMN_COUNT;
import static lma.constants.BotHandlerConstants.BRAND_PREFIX;
import static lma.constants.BotHandlerConstants.CHAT_ID_FOR_EMPTY_MESSAGE;
import static lma.constants.BotHandlerConstants.CHOOSE_BRAND_MESSAGE;
import static lma.constants.BotHandlerConstants.CHOOSE_CAR_MESSAGE;
import static lma.constants.BotHandlerConstants.CHOOSE_MODEL_MESSAGE_FORMAT;
import static lma.constants.BotHandlerConstants.DEFAULT_SCREEN_WIDTH_LENGTH_IN_LETTERS;
import static lma.constants.BotHandlerConstants.MODEL_PREFIX;
import static lma.constants.BotHandlerConstants.START_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_CLEARED_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_IS_EMPTY_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_MESSAGE;
import static lma.constants.BotHandlerConstants.UNSUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.UNSUBSCRIBE_MODEL_PREFIX;
import static lma.constants.CommonConstants.BRAND_MODEL_FORMAT;

@Component
@RequiredArgsConstructor
public class BotResponseHandler {

    private final UserService userService;

    private final BrandService brandService;

    private final ModelService modelService;

    private final PostService postService;

    private final PageParser pageParser;

    private final ScheduledService scheduledService;

    private final ModelCheckService modelCheckService;


    private SendMessage getEmptyMessage() {
        return SendMessage.builder()
                .chatId(CHAT_ID_FOR_EMPTY_MESSAGE)
                .text("")
                .build();
    }


    private SendMessage buildSendMessageWithReplyMarkup(Long chatId,
                                                        String text, InlineKeyboardMarkup inlineKeyboardMarkup) {

        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }


    public SendMessage handleStartCommand(Long chatId, Long userId) {
        userService.saveUserOrUpdateChatId(chatId, userId);

        return buildSendMessageWithReplyMarkup(chatId, START_MESSAGE, null);
    }


    public SendMessage handleSubscribeCommand(Long chatId, Long userId) {
        return getBrandsAsInlineKeyboardInSendMessage(chatId);
    }


    public SendMessage handleSubscriptionsCommand(Long chatId, Long userId) {
        return getSubscriptionListInSendMessage(chatId, userId, false);
    }


    public SendMessage handleUnsubscribeCommand(Long userId, Long chatId) {
        return getSubscriptionListInSendMessage(chatId, userId, true);
    }


    public SendMessage handleUnsubscribeAllCommand(Long userId, Long chatId) {
        User user = userService.findByIdWithModelsInitialized(userId);

        user.getModels().clear();

        userService.save(user);

        return buildSendMessageWithReplyMarkup(chatId, SUBSCRIPTION_LIST_CLEARED_MESSAGE, null);
    }


    public SendMessage handleSubscribe(Long userId, Long modelId) throws IOException {
        User user = userService.findByIdWithModelsInitialized(userId);

        List<Model> models = user.getModels();
        if (models == null) {
            models = new ArrayList<>();
        }
        Model model = modelService.findById(modelId);

        if (models.stream()
                .filter(modelInList -> modelInList.getId() == modelId)
                .findFirst()
                .isPresent()) {
            return buildSendMessageWithReplyMarkup(
                    user.getChatId(),
                    ALREADY_SUBSCRIBED_MESSAGE,
                    null
            );
        }

        models.add(model);

        userService.save(user);

        ModelCheck modelCheck = modelCheckService.findByModelId(modelId);
        if (modelCheck == null) {
            modelCheckService.updateModelCheckDate(modelCheck, modelId);
        }

        return buildSendMessageWithReplyMarkup(
                user.getChatId(),
                SUBSCRIBED_MESSAGE,
                null
        );
    }


    private SendMessage handleUnsubscribe(Long chatId, Long userId, Long modelId) {
        User user = userService.findByIdWithModelsInitialized(userId);

        user.getModels().removeIf(modelFromList ->
                modelFromList.getId().equals(modelId)
        );

        userService.save(user);

        return buildSendMessageWithReplyMarkup(chatId, UNSUBSCRIBED_MESSAGE, null);
    }


    public SendMessage handleCallbacks(Update update, SilentSender sender) throws InterruptedException, IOException {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (callbackQuery == null) {
                return getEmptyMessage();
            }
            String callbackQueryData = callbackQuery.getData();
            if (callbackQueryData == null) {
                return getEmptyMessage();
            }

            Long chatId = callbackQuery.getMessage().getChatId();
            Long userId = callbackQuery.getFrom().getId();

            userService.saveUserOrUpdateChatId(chatId, userId);

            if (callbackQueryData.contains(BRAND_PREFIX)) {
                return getModelListByBrandIdInSendMessage(chatId,
                        Long.parseLong(callbackQueryData.replace(BRAND_PREFIX, "")));

            } else if (callbackQueryData.contains(MODEL_PREFIX)) {
                return handleSubscribe(userId, Long.parseLong(callbackQueryData.replace(MODEL_PREFIX, "")));

            } else if (callbackQueryData.contains(UNSUBSCRIBE_MODEL_PREFIX)) {
                return handleUnsubscribe(chatId, userId,
                        Long.parseLong(callbackQueryData.replace(UNSUBSCRIBE_MODEL_PREFIX, "")));

            }
        }
        return getEmptyMessage();
    }


    public SendMessage getBrandsAsInlineKeyboardInSendMessage(Long chatId) {
        List<Brand> brands = brandService.findAll();
        List<InlineKeyboardRow> rows = new ArrayList<>();

        int count = 0;
        InlineKeyboardRow row = new InlineKeyboardRow();
        for (Brand brand : brands) {
            row.add(
                    createInlineKeyboardButton(
                            brand.getName(),
                            BRAND_PREFIX + brand.getId().toString()
                    )
            );
            count++;
            if (count % BRANDS_MESSAGE_COLUMN_COUNT == 0) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }

        return buildSendMessageWithReplyMarkup(chatId, CHOOSE_BRAND_MESSAGE, new InlineKeyboardMarkup(rows));
    }


    private InlineKeyboardButton createInlineKeyboardButton(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }


    private SendMessage getSubscriptionListInSendMessage(Long chatId, Long userId, boolean forRemoval) {
        String callbackDataPrefix = forRemoval ? UNSUBSCRIBE_MODEL_PREFIX : "";
        String message = forRemoval ? CHOOSE_CAR_MESSAGE : SUBSCRIPTION_LIST_MESSAGE;

        List<Model> models = modelService.findAllModelsBySubscriberId(userId);
        if (models.isEmpty()) {
            return buildSendMessageWithReplyMarkup(chatId, SUBSCRIPTION_LIST_IS_EMPTY_MESSAGE, null);
        }

        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Model model : models) {
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(
                    createInlineKeyboardButton(
                            BRAND_MODEL_FORMAT.formatted(model.getBrand().getName(), model.getName()),
                            callbackDataPrefix + model.getId().toString()
                    )
            );
            rows.add(row);
        }

        return buildSendMessageWithReplyMarkup(chatId, message, new InlineKeyboardMarkup(rows));
    }


    private SendMessage getModelListByBrandIdInSendMessage(Long chatId, Long brandId) {
        List<Model> models = modelService.findAllByBrandId(brandId);
        Brand brand = brandService.findById(brandId);

        int columnCount = DEFAULT_SCREEN_WIDTH_LENGTH_IN_LETTERS / models.stream()
                .map(model -> model.getName().length())
                .max(Integer::compare)
                .get();

        List<InlineKeyboardRow> rows = new ArrayList<>();

        int count = 0;
        InlineKeyboardRow row = new InlineKeyboardRow();
        for (Model model : models) {
            row.add(
                    createInlineKeyboardButton(
                            model.getName(),
                            MODEL_PREFIX + model.getId().toString()
                    )
            );
            count++;
            if (count % columnCount == 0) {
                rows.add(row);
                row = new InlineKeyboardRow();
            }
        }

        return buildSendMessageWithReplyMarkup(chatId,
                CHOOSE_MODEL_MESSAGE_FORMAT.formatted(brand.getName()), new InlineKeyboardMarkup(rows));
    }
}