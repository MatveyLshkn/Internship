package lma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lma.bot.CarBot;
import lma.dto.PostDto;
import lma.entity.User;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static lma.constants.CommonConstants.KAFKA_POST_GROUP_ID;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;
import static lma.constants.CommonConstants.TELEGRAM_POST_MESSAGE_FORMAT;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final CarBot bot;

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    @KafkaListener(topics = POST_KAFKA_TOPIC_NAME, groupId = KAFKA_POST_GROUP_ID)
    public void listen(PostDto post) throws JsonProcessingException, InterruptedException {

        Long modelId = post.modelId();

        List<User> subscribedUsers = userRepository.findAllBySubscribedModelId(modelId);

        for (User user : subscribedUsers) {
            bot.sendMessage(
                    SendMessage.builder()
                            .chatId(user.getChatId())
                            .text(TELEGRAM_POST_MESSAGE_FORMAT.formatted(post.url(), post.info()))
                            .build()
            );
            Thread.sleep(1000);
        }
    }
}