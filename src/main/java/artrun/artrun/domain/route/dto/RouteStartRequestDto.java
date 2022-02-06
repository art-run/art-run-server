package artrun.artrun.domain.route.dto;

import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.global.util.wktToGeometry;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RouteStartRequestDto {
    private Long memberId;
    private String wktTargetRoute;

    public Route toRoute() {
        Member member = Member.builder()
                .id(memberId)
                .build();
        return Route.builder()
                .member(member)
                .targetRoute(wktToGeometry.wktToGeometry(wktTargetRoute))
                .build();
    }
}
