package artrun.artrun.domain.route.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RouteMatchRequestDto {

    private Long routeId;
    private Double lat;
    private Double lng;

    @Override
    public String toString() {
        return "RouteMatchRequestDto{" +
                "routeId=" + routeId +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
