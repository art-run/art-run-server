package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteFinishRequestDto;
import artrun.artrun.domain.route.dto.RouteFinishResponseDto;
import artrun.artrun.domain.route.dto.RouteStartResponseDto;
import artrun.artrun.domain.route.dto.RouteStartRequestDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import artrun.artrun.global.util.wktToGeometry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.LineString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {

    @InjectMocks
    RouteService routeService;

    @Mock
    RouteRepository routeRepository;

    private Member member = Member.builder()
            .id(1L)
            .nickname("runnerA")
            .build();

    @BeforeAll
    static void setup() {
        mockStatic(SecurityUtil.class);
        given(SecurityUtil.isAuthorizedByMemberId(any())).willReturn(true);
    }

    @Test
    @DisplayName("달리기를 시작할 때, 멤버 아이디와 목표 경로를 받아서 새로운 경로를 생성한다.")
    void start() {
        // given
        LineString targetRoute = (LineString) wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        Route route = Route.builder()
                .member(member)
                .targetRoute(targetRoute)
                .build();
        given(routeRepository.save(any())).willReturn(route);
        given(routeRepository.getById(any())).willReturn(route);

        RouteStartRequestDto routeStartRequestDto = RouteStartRequestDto.builder().memberId(member.getId()).wktTargetRoute(String.valueOf(targetRoute)).build();

        // when
        RouteStartResponseDto routeResponseDto = routeService.startRoute(routeStartRequestDto);

        // then
        Route savedRoute = routeRepository.getById(routeResponseDto.getRouteId());
        assertThat(savedRoute.getTargetRoute().equals(targetRoute));
        assertThat(savedRoute.getMember().getNickname().equals(member.getNickname()));
    }

    @Test
    @DisplayName("달리기를 종료할 때, 달린 경로 포함 필요한 정보들을 받아서 경로 정보를 업데이트한다.")
    void finish() {
        //given
        String wktRunRoute = "LINESTRING (29 11, 11 31, 42 41)";
        String title = "달리기 성공!";

        RouteFinishRequestDto routeFinishRequestDto = RouteFinishRequestDto.builder()
                .routeId(1L)
                .memberId(member.getId())
                .wktRunRoute(wktRunRoute)
                .title(title)
                .distance(1235)
                .time(295)
                .kcal(64)
                .color("B00020")
                .thickness((byte) 3)
                .isPublic(Boolean.TRUE)
                .build();

        Route route = Route.builder()
                .id(1L)
                .member(member)
                .targetRoute(wktToGeometry.wktToGeometry("LINESTRING (29 11, 11 31, 42 41)"))
                .build();
        given(routeRepository.save(any())).willReturn(route);
        given(routeRepository.getByIdAndMemberId(any(), any())).willReturn(route);

        //when
        RouteFinishResponseDto routeFinishResponseDto = routeService.finishRoute(routeFinishRequestDto);

        //then
        Route savedRoute = routeRepository.getByIdAndMemberId(routeFinishResponseDto.getRouteId(), routeFinishRequestDto.getMemberId());
        assertThat(savedRoute.getRunRoute().toString().equals(wktRunRoute));
        assertThat(savedRoute.getTitle().equals(title));
    }

    @Test
    @DisplayName("유저가 경로 아이디를 이용해 본인의 경로를 삭제한다.")
    void deleteRoute() {
        // given
        Long memberId = 1L;
        Long routeId = 1L;
        Route route = Route.builder()
                .id(1L)
                .member(Member
                        .builder()
                        .id(memberId)
                        .build()
                )
                .targetRoute(wktToGeometry.wktToGeometry("LINESTRING (29 11, 11 31, 42 41)"))
                .runRoute(wktToGeometry.wktToGeometry("LINESTRING (29 11, 11 31, 42 41)"))
                .title("title")
                .distance(1235)
                .time(295)
                .kcal(64)
                .color("B00020")
                .thickness((byte) 3)
                .isPublic(Boolean.TRUE)
                .build();

        // when
        when(routeRepository.getById(any())).thenReturn(route);
        when(SecurityUtil.getCurrentMemberId()).thenReturn(memberId);

        // then
        routeService.deleteRoute(routeId);
    }
}