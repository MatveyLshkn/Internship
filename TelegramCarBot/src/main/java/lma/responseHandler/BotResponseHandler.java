package lma.responseHandler;

import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.User;
import lma.repository.BrandRepository;
import lma.repository.ModelRepository;
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
import static lma.constants.BotHandlerConstants.SUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.SUBSCRIPTION_LIST_CLEARED_MESSAGE;
import static lma.constants.BotHandlerConstants.UNSUBSCRIBED_MESSAGE;
import static lma.constants.BotHandlerConstants.UNSUBSCRIBE_MODEL_PREFIX;

@Component
@RequiredArgsConstructor
public class BotResponseHandler {

    private final UserRepository userRepository;

    private final BrandRepository brandRepository;

    private final ModelRepository modelRepository;

    public SendMessage handleStartCommand(Long chatId, Long userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            userRepository.save(User.builder()
                    .id(userId)
                    .chatId(chatId)
                    .build());
        }
        user.setChatId(chatId);

        return sendInlineKeyboardBrands(chatId);
    }

    public SendMessage sendInlineKeyboardBrands(Long chatId) {
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

    public SendMessage handleCallbacks(Update update) {
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            String callbackQueryData = callbackQuery.getData();

            Long chatId = callbackQuery.getMessage().getChatId();
            Long userId = callbackQuery.getFrom().getId();

            if (callbackQueryData.contains(BRAND_PREFIX)) {
                return sendModelListByBrand(chatId, Long.parseLong(callbackQueryData.replace(BRAND_PREFIX, "")));
            } else if (callbackQueryData.contains(MODEL_PREFIX)) {
                return handleSubscribe(chatId, Long.parseLong(callbackQueryData.replace(MODEL_PREFIX, "")));
            } else if (callbackQueryData.contains(UNSUBSCRIBE_MODEL_PREFIX)) {
                return unsubscribeUser(chatId, userId, Long.parseLong(callbackQueryData.replace(UNSUBSCRIBE_MODEL_PREFIX, "")));
            }
        }
        return null;
    }

    public SendMessage getSubscriptionList(Long chatId, Long userId){
        return sendSubscriptionList(chatId, userId, false);
    }

    public SendMessage handleUnsubscribe(Long userId, Long modelId) {
        return sendSubscriptionList(userId, modelId, true);
    }

    private SendMessage sendSubscriptionList(Long chatId, Long userId, boolean forRemoval) {
        String callbackDataPrefix = forRemoval ? UNSUBSCRIBE_MODEL_PREFIX : "";
        List<Model> models = modelRepository.findAllModelsBySubscriber(userId);

        return getSendMessageModelList(chatId, models);
    }

    private SendMessage sendModelListByBrand(Long chatId, Long brandId) {
        List<Model> models = modelRepository.findAllByBrand_Id(brandId);

        return getSendMessageModelList(chatId, models);
    }

    private SendMessage getSendMessageModelList(Long chatId, List<Model> models) {
        List<InlineKeyboardRow> rows = new ArrayList<>();

        for (Model model : models) {
            InlineKeyboardRow row = new InlineKeyboardRow();
            row.add(createInlineKeyboardButton(model.getName(), MODEL_PREFIX + model.getId().toString()));
            rows.add(row);
        }

        InlineKeyboardMarkup inlineKeyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboard(rows)
                .build();

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(CHOOSE_MODEL_MESSAGE)
                .replyMarkup(inlineKeyboardMarkup)
                .build();

        return message;
    }

    public SendMessage unsubscribeAllModels(Long userId, Long chatId) {
        User user = userRepository.findById(userId);

        user.getModels().clear();

        return SendMessage.builder()
                .chatId(chatId)
                .text(SUBSCRIPTION_LIST_CLEARED_MESSAGE)
                .build();
    }

    private SendMessage unsubscribeUser(Long chatId, Long userId, Long modelId) {
        User user = userRepository.findById(userId);
        Model model = modelRepository.findById(modelId);

        user.getModels().remove(model);

        return SendMessage.builder()
                .chatId(chatId)
                .text(UNSUBSCRIBED_MESSAGE)
                .build();
    }

    public SendMessage handleSubscribe(Long userId, Long modelId) {
        User user = userRepository.findById(userId);
        Model model = modelRepository.findById(modelId);

        user.getModels().add(model);

        return SendMessage.builder()
                .chatId(user.getChatId())
                .text(SUBSCRIBED_MESSAGE)
                .build();
    }
}
