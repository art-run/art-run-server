package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.BaseTestController;
import artrun.artrun.domain.route.dto.RouteStartRequestDto;
import artrun.artrun.domain.route.dto.RouteStartResponseDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import artrun.artrun.domain.route.service.RouteFindService;
import artrun.artrun.domain.route.service.RouteRunService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RouteController.class)
class RouteControllerTest extends BaseTestController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RouteRunService routeRunService;

    @MockBean
    RouteFindService routeFindService;

    @MockBean
    RouteRepository routeRepository;

    @Test
    void get() {
    }

    @Test
    void start() throws Exception {
        // given
        RouteStartRequestDto routeStartRequestDto = RouteStartRequestDto.builder()
                .memberId(1L)
                .wktTargetRoute("LINESTRING (29 11, 11 31, 42 41)")
                .build();

        // when
        RouteStartResponseDto routeStartResponseDto = new RouteStartResponseDto(3L);
        when(routeRunService.start(any())).thenReturn(routeStartResponseDto);

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
    void finish() {
    }
}