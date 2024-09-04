package lma.config;

import lma.constants.CommonConstants;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

import static lma.constants.CommonConstants.KAFKA_PARTITION_COUNT;
import static lma.constants.CommonConstants.KAFKA_REPLICA_COUNT;
import static lma.constants.CommonConstants.POST_KAFKA_TOPIC_NAME;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic postTopic() {
        return TopicBuilder.name(POST_KAFKA_TOPIC_NAME)
                .partitions(KAFKA_PARTITION_COUNT)
                .replicas(KAFKA_REPLICA_COUNT)
                .build();
    }
}
