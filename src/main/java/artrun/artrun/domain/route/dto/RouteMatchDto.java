package artrun.artrun.domain.route.dto;

import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.LineString;

@Getter
@Setter
public class RouteMatchDto {

    private Long routeId;
    private String wktTargetRoute;
    private Double lat;
    private Double lng;

    @Override
    public String toString() {
        return "RouteMatchDto{" +
                "routeId=" + routeId +
                ", wktTargetRoute='" + wktTargetRoute + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
