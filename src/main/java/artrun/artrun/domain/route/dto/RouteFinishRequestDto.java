package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.global.util.wktToGeometry;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteFinishRequestDto {
    private Long routeId;
    private Long memberId;
    private String wktRunRoute;
    private String title;
    private int distance;
    private int time;
    private int kcal;
    private String color;
    private Byte thickness;
    private Boolean isPublic;

    public Route toRoute() {
        return Route.builder()
                .runRoute(wktToGeometry.wktToGeometry(wktRunRoute))
                .title(title)
                .distance(distance)
                .time(time)
                .kcal(kcal)
                .color(color)
                .thickness(thickness)
                .isPublic(isPublic)
                .build();
    }
}
