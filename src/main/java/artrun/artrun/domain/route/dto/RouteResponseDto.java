package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteResponseDto {
    private String wktRunRoute;
    private String title;
    private int distance;
    private int time;
    private float speed;
    private int kcal;
    private String color;
    private Boolean isPublic;
    private LocalDateTime createdAt;

    public static RouteResponseDto of(Route route) {
        return RouteResponseDto.builder()
                .wktRunRoute(route.getRunRoute().toString())
                .title(route.getTitle())
                .distance(route.getDistance())
                .time(route.getTime())
                .speed(((float)route.getDistance()/1000) / (((float)route.getTime()/3600)))
                .kcal(route.getKcal())
                .color(route.getColor())
                .isPublic(route.getIsPublic())
                .createdAt(route.getCreatedAt())
                .build();
    }
}
