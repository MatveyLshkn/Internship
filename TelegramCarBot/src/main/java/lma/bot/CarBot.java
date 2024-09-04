package lma.bot;

import lma.entity.Brand;
import lma.entity.Model;
import lma.entity.User;
import lma.repository.BrandRepository;
import lma.repository.ModelRepository;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CarBot extends TelegramLongPollingBot {

    private final UserRepository userRepository;

    private final BrandRepository brandRepository;

    private final ModelRepository modelRepository;

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            Long userId = update.getMessage().getFrom().getId();

            if (update.getMessage().equals("/start")) {


                User user = userRepository.findById(userId);
                if (user == null) {
                    userRepository.save(User.builder()
                            .id(userId)
                            .chatId(chatId)
                            .build());
                }
                user.setChatId(chatId);

                sendInlineKeyboardBrands(chatId, "Choose car brand:");
            } else {
                sendText(chatId, "Wrong command!");
            }

        } else if (update.hasCallbackQuery()) {
            Long userId = update.getCallbackQuery().getFrom().getId();
            Long chatId = update.getCallbackQuery().getMessage().getChatId();

            User user = userRepository.findById(userId);
            if (user == null) {
                userRepository.save(User.builder()
                        .id(userId)
                        .chatId(chatId)
                        .build());
            }
            user.setChatId(chatId);


            String data = update.getCallbackQuery().getData();
            if (data.contains("BRAND_")) {
                sendModelList(chatId, Long.valueOf(data.replace("BRAND_" , "")));
            } else if (data.contains("MODEL_")) {
                handleSubscribe(chatId, Long.valueOf(data.replace("MODEL_" , "")));
            } else if(data.contains("UNSUBSCRIBE_")){
                handleUnsubscribe(userId, Long.valueOf(data.replace("UNSUBSCRIBE_" , "")));
                sendText(chatId, "Unsubscribed!");
            }
        }
    }

    public void handleUnsubscribe(Long userId, Long modelId) {
        User user = userRepository.findById(userId);
        Model model = modelRepository.findById(modelId);

        user.getModels().remove(model);
    }


    public void handleSubscribe(Long userId, Long modelId) {
        User user = userRepository.findById(userId);
        Model model = modelRepository.findById(modelId);

        user.getModels().add(model);
    }

    private void sendInlineKeyboardBrands(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();


        List<Brand> brands = brandRepository.findAll();
        for (Brand brand : brands) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(createInlineKeyboardButton(brand.getName(), "BRAND_" + brand.getId().toString()));
            rows.add(row);
        }


        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendSubscriptionList(Long chatId, Long userId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите модель: ");


        List<Model> models = modelRepository.findAllModelsBySubscriber(userId);


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Model model : models) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(createInlineKeyboardButton(model.getName(), "UNSUBSCRIBE_" + model.getId().toString()));
            rows.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void sendModelList(Long chatId, Long brandId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText("Выберите модель: ");


        List<Model> models = modelRepository.findAllByBrand_Id(brandId);


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rows = new ArrayList<>();

        for (Model model : models) {
            List<InlineKeyboardButton> row = new ArrayList<>();
            row.add(createInlineKeyboardButton(model.getName(), "MODEL_" + model.getId().toString()));
            rows.add(row);
        }

        inlineKeyboardMarkup.setKeyboard(rows);
        message.setReplyMarkup(inlineKeyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleModelSelection(Long chatId) {
        String responseMessage = "Модель выбрана";
        sendText(chatId, responseMessage);
    }



    private InlineKeyboardButton createInlineKeyboardButton(String text, String callbackData) {
        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText(text);
        button.setCallbackData(callbackData);
        return button;
    }


    @Override
    public String getBotUsername() {
        return "CarsAvBy_bot";
    }

    @Async
    public void sendText(Long who, String what) {
        SendMessage sm = SendMessage.builder()
                .chatId(who.toString())
                .text(what).build();
        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotToken() {
        return "7359286617:AAH0017cW6sIW7vVA_5wAdiI8_0gS_W_zJc";
    }
}
