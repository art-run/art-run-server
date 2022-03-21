package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.RouteMatchDto;
import artrun.artrun.global.util.kafka.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Slf4j
public class RouteMatchController {

    private final Sender sender;

    private static String KAKFA_TOPIC = "mapmatch";

    @MessageMapping("/match")
    public void match(RouteMatchDto routeMatchDto) throws JsonProcessingException {
        log.info("Send Message to Kafka: " + routeMatchDto.toString());
        sender.send(KAKFA_TOPIC, new ObjectMapper().writeValueAsString(routeMatchDto));
    }
}
