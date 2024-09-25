package lma.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lma.constants.AsyncConfigConstants;
import lma.constants.CommonConstants;
import lma.dto.PostDto;
import lma.entity.User;
import lma.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static lma.constants.CommonConstants.KAFKA_POST_GROUP_ID;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;
import static lma.constants.CommonConstants.TELEGRAM_POST_MESSAGE_FORMAT;
import static lma.constants.CommonConstants.THREAD_POLL_TASK_EXECUTOR_NAME;

@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final MessageService messageService;

    private final UserService userService;

    @Async(THREAD_POLL_TASK_EXECUTOR_NAME)
    @KafkaListener(topics = POST_KAFKA_TOPIC_NAME, groupId = KAFKA_POST_GROUP_ID)
    public void listen(PostDto post) throws InterruptedException {

        Long modelId = post.modelId();

        List<User> subscribedUsers = userService.findAllBySubscribedModelId(modelId);

        for (User user : subscribedUsers) {
            messageService.sendMessage(
                    String.valueOf(user.getChatId()),
                    TELEGRAM_POST_MESSAGE_FORMAT.formatted(post.url(), post.info())
            );
            Thread.sleep(1000);
        }
    }
}