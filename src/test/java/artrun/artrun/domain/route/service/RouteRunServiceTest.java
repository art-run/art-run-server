package artrun.artrun.domain.route.service;

import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.repository.RouteRepository;
import artrun.artrun.global.util.wktToGeometry;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.io.ParseException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RouteRunServiceTest {

    @InjectMocks
    RouteRunService routeRunService;

    @Mock
    RouteRepository routeRepository;

    private Member member = Member.builder()
            .id(1L)
            .nickname("runnerA")
            .build();

    @Test
    void start() throws ParseException {
        // given
        LineString targetRoute = (LineString) wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        Route route = Route.builder()
                .member(member)
                .targetRoute(targetRoute)
                .build();
        given(routeRepository.save(any())).willReturn(route);

        // when
        Route savedRoute = routeRunService.start(route);

        // then
        assertThat(savedRoute.getTargetRoute().equals(targetRoute));
        assertThat(savedRoute.getMember().getNickname().equals(member.getNickname()));
    }

    @Test
    void finish() throws ParseException {
        //given
        LineString targetRoute = (LineString) wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        LineString runRoute = (LineString) wktToGeometry.wktToGeometry("LINESTRING (29 11, 11 31, 42 41)");
        String title = "달리기 성공!";

        Route route = Route.builder()
                .id(1L)
                .member(member)
                .targetRoute(targetRoute)
                .runRoute(runRoute)
                .title(title)
                .distance(1235)
                .time(295)
                .kcal(64)
                .color("B00020")
                .thickness((byte) 3)
                .isPublic(Boolean.TRUE)
                .build();
        given(routeRepository.save(any())).willReturn(route);

        //when
        Route savedRoute = routeRunService.finish(route);

        //then
        assertThat(savedRoute.getRunRoute().equals(runRoute));
        assertThat(savedRoute.getTitle().equals(title));
    }
}