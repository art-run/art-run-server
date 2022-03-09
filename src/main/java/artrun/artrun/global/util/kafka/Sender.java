package artrun.artrun.global.util.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Sender {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void send(String topic, String data) {
        log.info("sending data='{}' to topic='{}'", data, topic);
        kafkaTemplate.send(topic, data);
    }
}
