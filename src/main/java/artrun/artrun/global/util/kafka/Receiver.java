package artrun.artrun.global.util.kafka;

import artrun.artrun.domain.route.dto.RouteMatchDto;
import artrun.artrun.domain.route.service.RouteService;
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

    private final RouteService routeService;

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(id="main-listener", topics = "mapmatch")
    public void receiveMapMatch(String message) throws JsonProcessingException {
        RouteMatchDto routeMatchDto = new ObjectMapper().readValue(message, RouteMatchDto.class);
        RouteMatchDto snappedRouteMatchDto = routeService.snapToTargetRoute(routeMatchDto);
        log.info("Kafka listen: " + snappedRouteMatchDto.toString());
        messagingTemplate.convertAndSend("/sub/match/" + snappedRouteMatchDto.getRouteId(), snappedRouteMatchDto);
    }
}
