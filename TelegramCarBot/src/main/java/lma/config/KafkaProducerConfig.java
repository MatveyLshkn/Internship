package lma.config;

import lma.constants.JsonConstants;
import lma.dto.PostDto;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static lma.constants.CommonConstants.KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME;

@Configuration
public class KafkaProducerConfig {

    @Value(KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME)
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, PostDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<PostDto>());
    }

    @Bean
    public KafkaTemplate<String, PostDto> kafkaTemplate(ProducerFactory<String, PostDto> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }
}
