package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.route.domain.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RouteStartResponseDto {
    private Long RouteId;

    public static RouteStartResponseDto of(Route route) {
        return new RouteStartResponseDto(route.getId());
    }
}
