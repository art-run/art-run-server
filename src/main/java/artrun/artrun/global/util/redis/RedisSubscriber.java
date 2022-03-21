package artrun.artrun.global.util.redis;

import artrun.artrun.domain.route.dto.RouteMatchDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber implements MessageListener {
    private final RedisTemplate redisTemplate;
    private final SimpMessageSendingOperations messagingTemplate;

    /**
     * Redis에서 메시지가 발행(publish)되면 대기하고 있던 onMessage가 해당 메시지를 받아 처리한다.
     */
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            // redis에서 발행된 데이터를 받아 deserialize
            String subscribedMessage = (String) redisTemplate.getStringSerializer().deserialize(message.getBody());
            log.info("Redis to Server listen: " + subscribedMessage);

            // routeMatchDto 객채로 맵핑
            RouteMatchDto routeMatchDto = new ObjectMapper().readValue(subscribedMessage, RouteMatchDto.class);

            // Websocket 구독자에게 채팅 메시지 Send
            messagingTemplate.convertAndSend("/sub/match/" + routeMatchDto.getRouteId(), routeMatchDto);

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
