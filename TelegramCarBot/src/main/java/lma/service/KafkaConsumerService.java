package lma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lma.bot.CarBot;
import lma.dto.PostDto;
import lma.entity.User;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;

import java.util.List;

import static lma.constants.CommonConstants.KAFKA_POST_GROUP_ID;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;
import static lma.constants.CommonConstants.TELEGRAM_POST_MESSAGE;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final CarBot bot;

    private final UserRepository userRepository;

    @KafkaListener(topics = POST_KAFKA_TOPIC_NAME, groupId = KAFKA_POST_GROUP_ID)
    public void listen(PostDto post) throws JsonProcessingException {

        Long modelId = post.modelId();

        List<User> subscribedUsers = userRepository.findAllBySubscribedModelId(modelId);

        SilentSender sender = bot.getSilent();
        for (User user : subscribedUsers) {
            sender.send(TELEGRAM_POST_MESSAGE.formatted(post.url(), post.info()), user.getChatId());
        }
    }
}