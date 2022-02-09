package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.global.util.wktToGeometry;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteFinishRequestDto {
    @Positive
    private Long routeId;

    @Positive
    private Long memberId;

    @NotEmpty
    private String wktRunRoute;

    @NotEmpty
    private String title;

    @PositiveOrZero
    private int distance;

    @PositiveOrZero
    private int time;

    @PositiveOrZero
    private int kcal;

    @NotEmpty
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
