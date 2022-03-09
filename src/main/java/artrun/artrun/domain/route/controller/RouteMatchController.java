package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.RouteMatchRequestDto;
import artrun.artrun.global.util.kafka.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;

@RequiredArgsConstructor
@Slf4j
public class RouteMatchController {

    private final Sender sender;

    private static String KAKFA_TOPIC = "mapmatch";

    @MessageMapping("/match")
    public void match(RouteMatchRequestDto routeMatchRequestDto) throws JsonProcessingException {
        log.info("Send Message to Kafka: " + routeMatchRequestDto.toString());
        sender.send(KAKFA_TOPIC, new ObjectMapper().writeValueAsString(routeMatchRequestDto));
    }
}
