package lma.responseHandler;

import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.Post;
import lma.entity.User;
import lma.service.BrandService;
import lma.service.ModelService;
import lma.service.PostService;
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

import java.util.ArrayList;
import java.util.List;

import static lma.constants.BotHandlerConstants.BRAND_PREFIX;
import static lma.constants.BotHandlerConstants.CHAT_ID_FOR_EMPTY_MESSAGE;
import static lma.constants.BotHandlerConstants.CHOOSE_BRAND_MESSAGE;
import static lma.constants.BotHandlerConstants.CHOOSE_MODEL_MESSAGE;
import static lma.constants.BotHandlerConstants.MODEL_PREFIX;
import static lma.constants.BotHandlerConstants.NO_BUTTON_TEXT;
import static lma.constants.BotHandlerConstants.POSTS_COUNT_MESSAGE_FORMAT;
import static lma.constants.BotHandlerConstants.POSTS_PREFIX;
import static lma.constants.BotHandlerConstants.START_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_CLEARED_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_IS_EMPTY_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_MESSAGE;
import static lma.constants.BotHandlerConstants.UNSUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.UNSUBSCRIBE_MODEL_PREFIX;
import static lma.constants.BotHandlerConstants.YES_BUTTON_TEXT;
import static lma.constants.CommonConstants.BRAND_MODEL_FORMAT;
import static lma.constants.CommonConstants.TELEGRAM_POST_MESSAGE;

@Component
@RequiredArgsConstructor
public class BotResponseHandler {

    private final UserService userService;

    private final BrandService brandService;

    private final ModelService modelService;

    private final PostService postService;


    private SendMessage getEmptyMessage() {
        return SendMessage.builder()
                .chatId(CHAT_ID_FOR_EMPTY_MESSAGE)
                .text("")
                .build();
    }


    private SendMessage buildSendMessage(Long chatId, String text, InlineKeyboardMarkup inlineKeyboardMarkup) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(inlineKeyboardMarkup)
                .build();
    }


    public SendMessage handleStartCommand(Long chatId, Long userId) {
        userService.saveUserOrUpdateChatId(chatId, userId);

        return buildSendMessage(chatId, START_MESSAGE, null);
    }


    public SendMessage handleSubscribeCommand(Long chatId, Long userId) {
        return getBrandsAsInlineKeyboardInSendMessage(chatId);
    }


    public SendMessage handleCallbacks(Update update, SilentSender sender) throws InterruptedException {
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

            if (callbackQueryData.contains(BRAND_PREFIX)) {
                return getModelListByBrandIdInSendMessage(chatId,
                        Long.parseLong(callbackQueryData.replace(BRAND_PREFIX, "")));

            } else if (callbackQueryData.contains(MODEL_PREFIX)) {
                return handleSubscribe(userId, Long.parseLong(callbackQueryData.replace(MODEL_PREFIX, "")));

            } else if (callbackQueryData.contains(UNSUBSCRIBE_MODEL_PREFIX)) {
                return handleUnsubscribe(chatId, userId,
                        Long.parseLong(callbackQueryData.replace(UNSUBSCRIBE_MODEL_PREFIX, "")));

            } else if (callbackQueryData.contains(POSTS_PREFIX)) {
                sendAllPostsByModelToUser(chatId,
                        Long.parseLong(callbackQueryData.replace(POSTS_PREFIX, "")),
                        sender
                );

            }
        }
        return getEmptyMessage();
    }


    public void sendAllPostsByModelToUser(Long chatId, Long modelId, SilentSender sender) throws InterruptedException {
        List<Post> posts = postService.findAllByModelId(modelId);
        for (Post post : posts) {
            sender.execute(
                    buildSendMessage(chatId,
                            TELEGRAM_POST_MESSAGE.formatted(post.getUrl(), post.getInfo()),
                            null
                    )
            );
            Thread.sleep(1000);
        }
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

        return buildSendMessage(chatId, SUBSCRIPTION_LIST_CLEARED_MESSAGE, null);
    }


    public SendMessage handleSubscribe(Long userId, Long modelId) {
        User user = userService.findByIdWithModelsInitialized(userId);

        List<Model> models = user.getModels();
        if (models == null) {
            models = new ArrayList<>();
        }
        Model model = modelService.findById(modelId);
        models.add(model);

        userService.save(user);

        InlineKeyboardButton yesKeyboardButton = createInlineKeyboardButton(
                YES_BUTTON_TEXT,
                POSTS_PREFIX + modelId
        );

        InlineKeyboardButton noKeyboardButton = createInlineKeyboardButton(
                NO_BUTTON_TEXT,
                String.valueOf(modelId)
        );

        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();
        inlineKeyboardRow.add(yesKeyboardButton);
        inlineKeyboardRow.add(noKeyboardButton);

        Long postsCount = postService.countPostsByModelId(modelId);

        return buildSendMessage(user.getChatId(),
                SUBSCRIBED_MESSAGE + POSTS_COUNT_MESSAGE_FORMAT.formatted(postsCount),
                new InlineKeyboardMarkup(List.of(inlineKeyboardRow))
        );
    }


    private SendMessage handleUnsubscribe(Long chatId, Long userId, Long modelId) {
        User user = userService.findByIdWithModelsInitialized(userId);

        user.getModels().removeIf(modelFromList ->
                modelFromList.getId().equals(modelId)
        );

        userService.save(user);

        return buildSendMessage(chatId, UNSUBSCRIBED_MESSAGE, null);
    }


    public SendMessage getBrandsAsInlineKeyboardInSendMessage(Long chatId) {
        List<Brand> brands = brandService.findAll();
        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Brand brand : brands) {
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(
                    createInlineKeyboardButton(
                            brand.getName(),
                            BRAND_PREFIX + brand.getId().toString()
                    )
            );
            rows.add(row);
        }

        return buildSendMessage(chatId, CHOOSE_BRAND_MESSAGE, new InlineKeyboardMarkup(rows));
    }


    private InlineKeyboardButton createInlineKeyboardButton(String text, String callbackData) {
        return InlineKeyboardButton.builder()
                .text(text)
                .callbackData(callbackData)
                .build();
    }


    private SendMessage getSubscriptionListInSendMessage(Long chatId, Long userId, boolean forRemoval) {
        String callbackDataPrefix = forRemoval ? UNSUBSCRIBE_MODEL_PREFIX : "";
        String message = forRemoval ? CHOOSE_MODEL_MESSAGE : SUBSCRIPTION_LIST_MESSAGE;

        List<Model> models = modelService.findAllModelsBySubscriberId(userId);
        if (models.isEmpty()) {
            return buildSendMessage(chatId, SUBSCRIPTION_LIST_IS_EMPTY_MESSAGE, null);
        }

        return getModelListInSendMessage(chatId, models, callbackDataPrefix, message);
    }


    private SendMessage getModelListByBrandIdInSendMessage(Long chatId, Long brandId) {
        List<Model> models = modelService.findAllByBrandId(brandId);

        return getModelListInSendMessage(chatId, models, MODEL_PREFIX, CHOOSE_MODEL_MESSAGE);
    }


    private SendMessage getModelListInSendMessage(Long chatId, List<Model> models,
                                                  String modelPrefix, String message) {

        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Model model : models) {
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(
                    createInlineKeyboardButton(
                            BRAND_MODEL_FORMAT.formatted(model.getBrand().getName(), model.getName()),
                            modelPrefix + model.getId().toString()
                    )
            );
            rows.add(row);
        }

        return buildSendMessage(chatId, message, new InlineKeyboardMarkup(rows));
    }
}