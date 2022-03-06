package artrun.artrun.domain.auth.exception;

import artrun.artrun.domain.auth.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StompHandler implements ChannelInterceptor {

    private final TokenProvider tokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("message:" + message);
        log.info("token: " + accessor.getNativeHeader("Authorization"));

        // websocket 연결시 헤더의 jwt token 검증
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            tokenProvider.validateToken(accessor.getFirstNativeHeader("Authorization"));
        }
        return message;
    }
}