package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.LineString;

@Getter
@Setter
@AllArgsConstructor
public class RouteResponseDto {
    private LineString targetRoute;

    public static RouteResponseDto of(Route route) {
        return new RouteResponseDto(route.getTargetRoute());
    }
}
