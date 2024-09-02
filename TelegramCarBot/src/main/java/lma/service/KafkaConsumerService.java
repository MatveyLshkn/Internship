package lma.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = "post", groupId = "post-group")
    public void listen(String message) {

        //TODO send messages to subscribers
        System.out.println("Received Message: " + message);
    }
}
