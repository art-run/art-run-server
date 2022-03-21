package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.RouteMatchDto;
import artrun.artrun.global.util.kafka.KafkaSender;
import artrun.artrun.global.util.redis.RedisSubscriber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RouteMatchController {

    private final KafkaSender kafkaSender;

    private final RedisMessageListenerContainer redisMessageListenerContainer;

    private final RedisSubscriber redisSubscriber;

    @MessageMapping("/match/{routeId}/create")
    public void start(@DestinationVariable Long routeId) {
        // Redis Topic Subscribe
        log.info("Redis Subscribe start: " + routeId);
        ChannelTopic channelTopic = new ChannelTopic("match:" + routeId);
        redisMessageListenerContainer.addMessageListener(redisSubscriber, channelTopic);
    }

    @MessageMapping("/match/send")
    public void send(RouteMatchDto routeMatchDto) throws JsonProcessingException {
        kafkaSender.send("mapmatch", new ObjectMapper().writeValueAsString(routeMatchDto));
    }
}
