package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteCardResponseDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import artrun.artrun.global.util.wktToGeometry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Geometry;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteFindServiceTest {

    @InjectMocks
    RouteFindService routeFindService;

    @Mock
    RouteRepository routeRepository;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeAll
    public static void beforeAll() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }

    @AfterAll
    public static void afterAll() {
        securityUtilMockedStatic.close();
    }

    @DisplayName("getPublicRoutes 첫 페이지")
    @Test
    void getPublicRoutesFirst() {
        // given
        Geometry route = wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        Member member = Member.builder().id(1L).nickname("test").profileImg("testurl").build();
        List<Route> routeList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            routeList.add(Route.builder()
                    .id((long) i)
                    .member(member)
                    .targetRoute(route)
                    .runRoute(route)
                    .title("title" + i)
                    .distance(1000)
                    .time(3600)
                    .kcal(104)
                    .color("000000")
                    .thickness((byte) 3)
                    .isPublic(true)
                    .build());
        }
        given(routeRepository.getPublicRoutes(any())).willReturn(routeList.subList(25,30));

        // when
        List<RouteCardResponseDto> routeCards = routeFindService.getPublicRoutes(null);

        // then
        assertThat(routeCards.get(0).getRouteId().equals(30L));
        assertThat(routeCards.get(0).getNickname().equals(member.getNickname()));
        assertThat(routeCards.get(0).getProfileImg().equals(member.getProfileImg()));
        assertThat(routeCards.get(4).getRouteId().equals(26L));
        assertThat(routeCards.get(4).getTitle().equals("title26"));
        assertThat(routeCards.get(4).getWktRunRoute().equals(route.toString()));
    }

    @Test
    @DisplayName("getMyRoutes 첫 페이지")
    void getMyRoutes() {
        // given
        Long lastRouteId = null;
        Member member = Member.builder().id(1L).nickname("test").profileImg("testurl").build();
        Geometry route = wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        List<Route> routeList = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            routeList.add(Route.builder()
                    .id((long) i)
                    .member(member)
                    .targetRoute(route)
                    .runRoute(route)
                    .title("title" + i)
                    .distance(1000)
                    .time(3600)
                    .kcal(104)
                    .color("000000")
                    .thickness((byte) 3)
                    .isPublic(true)
                    .build());
        }

        // when
        when(SecurityUtil.isAuthorizedByMemberId(any())).thenReturn(true);
        when(SecurityUtil.getCurrentMemberId()).thenReturn(member.getId());
        when(routeRepository.getRoutesByMemberId(member.getId(), lastRouteId)).thenReturn(routeList);

        // then
        List<RouteCardResponseDto> routeCards = routeFindService.getMyRoutes(null);
        assertThat(routeCards.get(0).getRouteId().equals(30L));
        assertThat(routeCards.get(0).getNickname().equals(member.getNickname()));
        assertThat(routeCards.get(0).getProfileImg().equals(member.getProfileImg()));
        assertThat(routeCards.get(4).getRouteId().equals(26L));
        assertThat(routeCards.get(4).getTitle().equals("title26"));
        assertThat(routeCards.get(4).getWktRunRoute().equals(route.toString()));
    }
}