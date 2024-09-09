package lma.responseHandler;

import jakarta.transaction.Transactional;
import lma.constants.CommonConstants;
import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.User;
import lma.repository.BrandRepository;
import lma.repository.ModelRepository;
import lma.repository.PostRepository;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

import static lma.constants.BotHandlerConstants.BRAND_PREFIX;
import static lma.constants.BotHandlerConstants.CHOOSE_BRAND_MESSAGE;
import static lma.constants.BotHandlerConstants.CHOOSE_MODEL_MESSAGE;
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

    private final UserRepository userRepository;

    private final BrandRepository brandRepository;

    private final ModelRepository modelRepository;

    private final PostRepository postRepository;


    public SendMessage handleStartCommand(Long chatId, Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            user = userRepository.save(User.builder()
                    .id(userId)
                    .chatId(chatId)
                    .build());
        }
        user.setChatId(chatId);

        return SendMessage.builder()
                .chatId(chatId)
                .text(START_MESSAGE)
                .build();
    }


    public SendMessage handleSubscribeCommand(Long chatId, Long userId) {
        return getBrandsAsInlineKeyboardInSendMessage(chatId);
    }


    public SendMessage handleCallbacks(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            if (callbackQuery == null) {
                return SendMessage.builder().build();
            }
            String callbackQueryData = callbackQuery.getData();
            if (callbackQueryData == null) {
                return SendMessage.builder().build();
            }

            Long chatId = callbackQuery.getMessage().getChatId();
            Long userId = callbackQuery.getFrom().getId();

            if (callbackQueryData.contains(BRAND_PREFIX)) {
                return getModelListByBrandInSendMessage(chatId, Long.parseLong(callbackQueryData.replace(BRAND_PREFIX, "")));
            } else if (callbackQueryData.contains(MODEL_PREFIX)) {
                return handleSubscribe(userId, Long.parseLong(callbackQueryData.replace(MODEL_PREFIX, "")));
            } else if (callbackQueryData.contains(UNSUBSCRIBE_MODEL_PREFIX)) {
                return handleUnsubscribe(chatId, userId, Long.parseLong(callbackQueryData.replace(UNSUBSCRIBE_MODEL_PREFIX, "")));
            } else if (callbackQueryData.contains("POSTS_")) {
                //TODO send all posts by model
            }
        }
        return SendMessage.builder().build();
    }


    public SendMessage handleSubscriptionsCommand(Long chatId, Long userId) {
        return getSubscriptionListInSendMessage(chatId, userId, false);
    }


    public SendMessage handleUnsubscribeCommand(Long userId, Long chatId) {
        return getSubscriptionListInSendMessage(chatId, userId, true);
    }


    public SendMessage handleUnsubscribeAllCommand(Long userId, Long chatId) {
        User user = userRepository.findByIdWithModelsInitialized(userId);

        user.getModels().clear();

        userRepository.save(user);

        return SendMessage.builder()
                .chatId(chatId)
                .text(SUBSCRIPTION_LIST_CLEARED_MESSAGE)
                .build();
    }

    public SendMessage handleSubscribe(Long userId, Long modelId) {
        User user = userRepository.findByIdWithModelsInitialized(userId);

        List<Model> models = user.getModels();
        if (models == null) {
            models = new ArrayList<>();
        }
        Model model = modelRepository.findById(modelId);
        models.add(model);

        userRepository.save(user);

        Long postsCount = postRepository.countPostByModel_Id(modelId);
        InlineKeyboardButton yesKeyboardButton = createInlineKeyboardButton(
                "YES",
                "POSTS_" + modelId
        );

        InlineKeyboardButton noKeyboardButton = createInlineKeyboardButton(
                "NO",
                "" + modelId
        );

        InlineKeyboardRow inlineKeyboardRow = new InlineKeyboardRow();
        inlineKeyboardRow.add(yesKeyboardButton);
        inlineKeyboardRow.add(noKeyboardButton);

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(List.of(inlineKeyboardRow))
                .build();

        return SendMessage.builder()
                .chatId(user.getChatId())
                .replyMarkup(inlineKeyboardMarkup)
                .text(SUBSCRIBED_MESSAGE + "\nThere are " + postsCount + " posts\n Do you wan\'t to watch them all?")
                .build();
    }


    private SendMessage handleUnsubscribe(Long chatId, Long userId, Long modelId) {
        User user = userRepository.findByIdWithModelsInitialized(userId);

        Model model = modelRepository.findById(modelId);

        user.getModels().removeIf(modelFromList ->
                modelFromList.getId().equals(model.getId())
        );

        userRepository.save(user);

        return SendMessage.builder()
                .chatId(chatId)
                .text(UNSUBSCRIBED_MESSAGE)
                .build();
    }


    public SendMessage getBrandsAsInlineKeyboardInSendMessage(Long chatId) {
        List<Brand> brands = brandRepository.findAll();
        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Brand brand : brands) {
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(createInlineKeyboardButton(brand.getName(), BRAND_PREFIX + brand.getId().toString()));
            rows.add(row);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(CHOOSE_BRAND_MESSAGE)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        return message;
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

        List<Model> models = modelRepository.findAllModelsBySubscriber(userId);
        if (models == null || models.isEmpty()) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(SUBSCRIPTION_LIST_IS_EMPTY_MESSAGE)
                    .build();
        }

        return getSendMessageModelList(chatId, models, callbackDataPrefix, message);
    }


    private SendMessage getModelListByBrandInSendMessage(Long chatId, Long brandId) {
        List<Model> models = modelRepository.findAllByBrand_Id(brandId);

        return getSendMessageModelList(chatId, models, MODEL_PREFIX, CHOOSE_MODEL_MESSAGE);
    }


    private SendMessage getSendMessageModelList(Long chatId, List<Model> models,
                                                String modelPrefix, String message) {

        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Model model : models) {
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(createInlineKeyboardButton(
                            BRAND_MODEL_FORMAT.formatted(model.getBrand().getName(), model.getName()),
                            modelPrefix + model.getId().toString()
                    )
            );
            rows.add(row);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(message)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        return sendMessage;
    }
}
