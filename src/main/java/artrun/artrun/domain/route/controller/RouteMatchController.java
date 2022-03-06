package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.RouteMatchRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@Slf4j
public class RouteMatchController {

    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/match")
    public void match(RouteMatchRequestDto routeMatchRequestDto) {
        log.info("Send Message: " + routeMatchRequestDto.toString());
        messagingTemplate.convertAndSend("/sub/match/" + routeMatchRequestDto.getRouteId(), routeMatchRequestDto);
    }
}
