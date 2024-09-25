package lma.service;

import lma.constants.BotConstants;
import lma.exception.TelegramSendMessageException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static lma.constants.BotConstants.BOT_TOKEN;
import static lma.constants.CommonConstants.SEND_MESSAGE_VIA_TG_API_URL_FORMAT;
import static lma.constants.CommonConstants.TELEGRAM_SEND_MESSAGE_EXCEPTION_MESSAGE;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final RestTemplate restTemplate;

    public void sendMessage(String chatId, String message) {
        String url = SEND_MESSAGE_VIA_TG_API_URL_FORMAT.formatted(BOT_TOKEN, chatId, message);

        try {
            restTemplate.getForObject(url, String.class);
        } catch (Exception e) {
            throw new TelegramSendMessageException(TELEGRAM_SEND_MESSAGE_EXCEPTION_MESSAGE, e);
        }
    }
}
