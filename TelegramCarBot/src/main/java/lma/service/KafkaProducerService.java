package lma.service;

import lma.constants.CommonConstants;
import lma.dto.PostReadDto;
import lma.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, PostReadDto> kafkaTemplate;

    public void sendMessage(PostReadDto post) {
        kafkaTemplate.send(POST_KAFKA_TOPIC_NAME, post);
    }
}