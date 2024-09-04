package lma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lma.bot.CarBot;
import lma.constants.CommonConstants;
import lma.dto.PostReadDto;
import lma.entity.Post;
import lma.entity.User;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

import static lma.constants.CommonConstants.KAFKA_POST_GROUP_ID;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;
import static lma.constants.CommonConstants.TELEGRAM_POST_MESSAGE;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final UserRepository userRepository;

    private final CarBot bot;

    @KafkaListener(topics = POST_KAFKA_TOPIC_NAME, groupId = KAFKA_POST_GROUP_ID)
    public void listen(PostReadDto post) throws JsonProcessingException {

        Long modelId = post.modelId();

        List<User> subscribedUsers = userRepository.findAllBySubscribedModelId(modelId);

        for (User user : subscribedUsers) {
            bot.sendText(user.getChatId(), TELEGRAM_POST_MESSAGE.formatted(post.url(), post.info()));
        }
    }
}