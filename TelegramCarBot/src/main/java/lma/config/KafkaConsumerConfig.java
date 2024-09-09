package lma.config;

import lma.dto.PostDto;
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
import static lma.constants.CommonConstants.KAFKA_TRUSTED_PACKAGES;


@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME)
    private String bootstrapServers;

    @Bean
    public ConsumerFactory<String, PostDto> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, KAFKA_POST_GROUP_ID);
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, KAFKA_TRUSTED_PACKAGES);
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
        configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, PostDto.class.getName());

        return new DefaultKafkaConsumerFactory<>(configs, new StringDeserializer(),
                new JsonDeserializer<>(PostDto.class)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PostDto> kafkaListenerContainerFactory(
            ConsumerFactory<String, PostDto> consumerFactory) {

        ConcurrentKafkaListenerContainerFactory<String, PostDto> factory = new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
