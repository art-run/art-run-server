package artrun.artrun.global.util.kafka;

import artrun.artrun.domain.route.dto.RouteMatchDto;
import artrun.artrun.global.util.redis.RedisPublisher;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaReceiver {

    private final RedisPublisher redisPublisher;

    @KafkaListener(id="main-listener", topics = "match/res")
    public void receiveMapMatch(String message) throws JsonProcessingException {
        log.info("Kafka to Server listen: " + message);
        RouteMatchDto routeMatchDto = new ObjectMapper().readValue(message, RouteMatchDto.class);
        ChannelTopic channelTopic = ChannelTopic.of("match:" + routeMatchDto.getRouteId());
        redisPublisher.publish(channelTopic, message);
    }
}
