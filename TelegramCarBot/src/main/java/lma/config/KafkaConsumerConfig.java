package lma.config;

import lma.constants.CommonConstants;
import lma.dto.PostReadDto;
import lma.entity.Post;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

import static lma.constants.CommonConstants.KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME;
import static lma.constants.CommonConstants.KAFKA_POST_GROUP_ID;


@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Value(KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME)
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, PostReadDto> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_POST_GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(props,  new StringDeserializer(), new JsonDeserializer<PostReadDto>());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostReadDto> kafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, PostReadDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
