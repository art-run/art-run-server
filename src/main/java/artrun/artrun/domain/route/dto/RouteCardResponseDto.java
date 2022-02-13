package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RouteCardResponseDto {

    private Long routeId;
    private String nickname;
    private String profileImg;
    private String title;
    private int distance;
    private LocalDateTime createdAt;
    private String wktRunRoute;

    public static RouteCardResponseDto of(Route route) {
        return RouteCardResponseDto.builder()
                .routeId(route.getId())
                .nickname(route.getMember().getNickname())
                .profileImg(route.getMember().getProfileImg())
                .title(route.getTitle())
                .distance(route.getDistance())
                .createdAt(route.getCreatedAt())
                .wktRunRoute(route.getRunRoute().toString())
                .build();
    }
}
