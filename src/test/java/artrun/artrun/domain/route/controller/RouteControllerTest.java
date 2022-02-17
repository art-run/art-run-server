package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.BaseTestController;
import artrun.artrun.domain.route.dto.*;
import artrun.artrun.domain.route.repository.RouteRepository;
import artrun.artrun.domain.route.service.RouteFindService;
import artrun.artrun.domain.route.service.RouteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityNotFoundException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
class RouteControllerTest extends BaseTestController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RouteService routeService;

    @MockBean
    RouteFindService routeFindService;

    @MockBean
    RouteRepository routeRepository;

    @Test
    @DisplayName("경로의 아이디를 조회할 때, 경로의 아이디가 없으면 ENTITY_NOT_FOUND 예외를 반환한다.")
    void getRouteNotfound() throws Exception{
        // given

        // when
        when(routeFindService.get(any())).thenThrow(new EntityNotFoundException("not found"));

        // then
        mockMvc.perform(get("/route/100")
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("code").value("C003"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("달리기를 시작할 때, 멤버 아이디와 목표 경로를 넘기면 새 경로의 아이디를 반환한다.")
    void start() throws Exception {
        // given
        RouteStartRequestDto routeStartRequestDto = RouteStartRequestDto.builder()
                .memberId(1L)
                .wktTargetRoute("LINESTRING (29 11, 11 31, 42 41)")
                .build();

        // when
        RouteStartResponseDto routeStartResponseDto = new RouteStartResponseDto(3L);
        when(routeService.startRoute(any())).thenReturn(routeStartResponseDto);

        // then
        String requestBody = objectMapper.writeValueAsString(routeStartRequestDto);
        mockMvc.perform(post("/route/start")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("routeId").value(routeStartResponseDto.getRouteId()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("달리기를 종료할 때, 달린 경로 포함 필요한 정보들을 넘기면 업데이트된 경로의 아이디를 반환한다.")
    void finish() throws Exception {
        // given
        RouteFinishRequestDto routeFinishRequestDto = RouteFinishRequestDto.builder()
                .routeId(1L)
                .memberId(1L)
                .wktRunRoute("LINESTRING (29 11, 11 31, 42 41)")
                .title("달리기 완료!")
                .distance(1235)
                .time(295)
                .kcal(64)
                .color("B00020")
                .thickness((byte) 3)
                .isPublic(Boolean.TRUE)
                .build();

        // when
        RouteFinishResponseDto routeFinishResponseDto = new RouteFinishResponseDto(1L);
        when(routeService.finishRoute(any())).thenReturn(routeFinishResponseDto);

        // then
        String requestBody = objectMapper.writeValueAsString(routeFinishRequestDto);
        mockMvc.perform(post("/route/finish")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("routeId").value(routeFinishRequestDto.getRouteId()))
                .andExpect(status().isOk());
    }
}