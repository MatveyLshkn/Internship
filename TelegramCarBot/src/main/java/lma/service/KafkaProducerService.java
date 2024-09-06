package lma.service;

import lma.dto.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, PostDto> kafkaTemplate;

    public void sendMessage(PostDto post) {
        kafkaTemplate.send(POST_KAFKA_TOPIC_NAME, post);
    }
}