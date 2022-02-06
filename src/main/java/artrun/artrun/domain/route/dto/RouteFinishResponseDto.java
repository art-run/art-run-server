package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RouteFinishResponseDto {
    Long routeId;

    public static RouteFinishResponseDto of(Route route) {
        return new RouteFinishResponseDto(route.getId());
    }
}
