package lma.config;

import lma.constants.CommonConstants;
import lma.dto.PostReadDto;
import lma.entity.Post;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static lma.constants.CommonConstants.KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME;

@Configuration
public class KafkaProducerConfig {

    @Value(KAFKA_BOOTSTRAP_SERVERS_CONFIG_NAME)
    private String bootstrapServers;

    @Bean
    public ProducerFactory<String, PostReadDto> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        return new DefaultKafkaProducerFactory<>(configProps, new StringSerializer(), new JsonSerializer<PostReadDto>());
    }

    @Bean
    public KafkaTemplate<String, PostReadDto> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
