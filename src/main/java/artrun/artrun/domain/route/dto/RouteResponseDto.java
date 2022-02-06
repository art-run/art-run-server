package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RouteResponseDto {
    private String targetRoute;

    public static RouteResponseDto of(Route route) {
        return new RouteResponseDto(route.getTargetRoute().toString());
    }
}
