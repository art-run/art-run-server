package artrun.artrun.global.util.kafka;

import artrun.artrun.domain.route.dto.RouteMatchRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Receiver {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(id="main-listener", topics = "mapmatch")
    public void receiveMapMatch(String message) throws JsonProcessingException {
        RouteMatchRequestDto routeMatchRequestDto = new ObjectMapper().readValue(message, RouteMatchRequestDto.class);
        log.info("Kafka listen: " + routeMatchRequestDto.toString());
        messagingTemplate.convertAndSend("/sub/match/" + routeMatchRequestDto.getRouteId(), routeMatchRequestDto);
    }
}
