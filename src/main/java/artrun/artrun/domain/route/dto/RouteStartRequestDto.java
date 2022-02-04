package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.global.util.wktToGeometry;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.io.ParseException;

@Getter
@Setter
public class RouteStartRequestDto {
    private Long memberId;
    private String wktTargetRoute;

    public Route toRoute() throws ParseException {
        Member member = Member.builder()
                .id(memberId)
                .build();
        return Route.builder()
                .member(member)
                .targetRoute(wktToGeometry.wktToGeometry(wktTargetRoute))
                .build();
    }
}
