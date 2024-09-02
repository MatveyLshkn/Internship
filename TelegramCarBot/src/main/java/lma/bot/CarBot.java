package lma.bot;

import org.springframework.scheduling.annotation.Async;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.concurrent.CompletableFuture;

public class CarBot extends TelegramLongPollingBot {


    @Override
    public void onUpdateReceived(Update update) {

    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Async
    public void sendText(Long who, String what){

        /*CompletableFuture.runAsync(() -> {
            SendMessage message = new SendMessage();
            message.setChatId(String.valueOf(chatId));
            message.setText("Вы отправили: " + messageText);

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        });*/



        SendMessage sm = SendMessage.builder()
                .chatId(who.toString()) //Who are we sending a message to
                .text(what).build();    //Message content
        try {
            execute(sm);                        //Actually sending the message
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);      //Any error will be printed here
        }
    }
}
