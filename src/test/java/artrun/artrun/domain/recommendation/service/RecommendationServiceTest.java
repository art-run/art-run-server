package artrun.artrun.domain.recommendation.service;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
import artrun.artrun.global.util.requester.OsrmRequester;
import artrun.artrun.global.util.wktToGeometry;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @InjectMocks
    RecommendationService recommendationService;

    @Mock
    RecommendationRepository recommendationRepository;

    @Mock
    OsrmRequester osrmRequester;

    @DisplayName("추천 경로들 조회: List<RecommendationResponseDto> 반환")
    @Test
    void getRecommendationsByDistance() {
        // given
        int distance = 3000;
        Pageable pageable = Pageable.ofSize(1);

        // when
        Geometry route = wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        List<Recommendation> recommendationList = new ArrayList<>();
        for(int i =1; i<= 5; i++) {
            recommendationList.add(Recommendation.builder()
                    .id((long) i)
                    .route(route)
                    .distance(100 * i)
                    .title("title" + i)
                    .usedCount((long) i)
                    .build());
        }
        when(recommendationRepository.getRecommendationsByDistance(anyInt(), any())).thenReturn(recommendationList);

        // then
        assertThat(recommendationService.getRecommendationsByDistance(distance, pageable).get(0)).isInstanceOf(RecommendationResponseDto.class);
        assertThat(recommendationService.getRecommendationsByDistance(distance, pageable).get(0).getTitle()).isEqualTo(recommendationList.get(0).getTitle());
    }

    @DisplayName("centerPosition과 centroid의 차이만큼 평행이동한다.")
    @Test
    void adjustRecommendationToCenter1() {
        // given
        Geometry route = wktToGeometry.wktToGeometry("LINESTRING(127.2676797373322 37.004810894968806,127.26727204156194 37.005033659711664,127.26705746484075 37.005376373426046,127.266563938382 37.00518788107435,127.26600603890691 37.004656672841335,127.26669268441472 37.004228276401264,127.26701454949651 37.00373133350641,127.26759390664373 37.00361138128649,127.26821617913518 37.00325152349115,127.26855950188909 37.00403978120286,127.26890282464299 37.00489657379332,127.26907448601995 37.005890441100696,127.26849512887273 37.005907576629994,127.26832346749578 37.00523928812571,127.26791577172551 37.004879438036134)");

        Recommendation recommendation = Recommendation.builder()
                .id(1L)
                .title("test")
                .distance(1234)
                .usedCount(1L)
                .route(route.copy())
                .build();
        double lat = 37.55694939550261;
        double lng = 126.98495686769316;

        // when
        when(recommendationRepository.getById(any())).thenReturn(recommendation);
        Geometry adjustedRoute = wktToGeometry.wktToGeometry("LINESTRING (126.98487040555975 37.557091611092346, 126.98446270978948 37.557314375835205, 126.98424813306829 37.557657089549586, 126.98375460660955 37.55746859719789, 126.98319670713445 37.556937388964876, 126.98388335264227 37.556508992524805, 126.98420521772405 37.55601204962995, 126.98478457487127 37.55589209741003, 126.98540684736273 37.55553223961469, 126.98575017011663 37.5563204973264, 126.98609349287054 37.55717728991686, 126.98626515424749 37.55817115722424, 126.98568579710027 37.558188292753535, 126.98551413572332 37.55752000424925, 126.98510643995306 37.557160154159675)");
        when(osrmRequester.requestMatch(any())).thenReturn(adjustedRoute);

        // then
        assertThat(recommendationService.adjustRecommendationToCenter(recommendation.getId(), lat, lng).getTitle()).isEqualTo(recommendation.getTitle());
        assertThat(recommendationService.adjustRecommendationToCenter(recommendation.getId(), lat, lng).getWktRoute()).isEqualTo(adjustedRoute.toString());

    }
}