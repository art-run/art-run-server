package artrun.artrun.domain.recommendation.controller;

import artrun.artrun.domain.BaseTestController;
import artrun.artrun.domain.member.controller.MemberController;
import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.service.RecommendationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RecommendationController.class)
class RecommendationControllerTest extends BaseTestController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RecommendationService recommendationService;

    @Test
    @DisplayName("추천 경로 보기: List<RecommendationResponseDto> 반환")
    void getRecommendations() throws Exception {
        // given
        int distance = 3000;
        int pageNumber = 1;

        // when
        List<RecommendationResponseDto> recommendations = new ArrayList<>();
        recommendations.add(RecommendationResponseDto.builder().id(1L).wktRoute("").title("heart").distance(1000).build());
        recommendations.add(RecommendationResponseDto.builder().id(2L).wktRoute("").title("star").distance(2500).build());
        when(recommendationService.getRecommendationsByDistance(anyInt(), any())).thenReturn(recommendations);

        // then
        mockMvc.perform(get("/recommendations")
                        .param("distance", String.valueOf(distance))
                        .param("pageNumber", String.valueOf(pageNumber))
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("$[0].title").value(recommendations.get(0).getTitle()))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("보정된 추천 경로 출력: RecommendationResponseDto 반환")
    void getAdjustedRecommendation() throws Exception {
        // given
        String recommendationId = "1";
        double lat = 37.55694939550261;
        double lng = 126.98495686769316;

        // when
        RecommendationResponseDto recommendationResponseDto = RecommendationResponseDto.builder().id(1L).title("heart").distance(1000).build();
        when(recommendationService.adjustRecommendationToCenter(any(), any(), any())).thenReturn(recommendationResponseDto);

        // then
        mockMvc.perform(get("/recommendation/"+recommendationId+"/adjust")
                        .param("lat", String.valueOf(lat))
                        .param("lng", String.valueOf(lng))
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("$.id").value(recommendationResponseDto.getId()))
                .andExpect(jsonPath("$.title").value(recommendationResponseDto.getTitle()))
                .andExpect(status().isOk());
    }

}