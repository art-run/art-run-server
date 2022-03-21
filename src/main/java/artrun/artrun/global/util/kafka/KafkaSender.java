package artrun.artrun.global.util.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaSender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String data) {
        log.info("Server to Kafka Send: {} to topic {}", data, topic);
        kafkaTemplate.send(topic, data);
    }
}
