package artrun.artrun.domain.recommendation.service;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
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


import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @InjectMocks
    RecommendationService recommendationService;

    @Mock
    RecommendationRepository recommendationRepository;

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
        double latOffset = lat - recommendation.getRoute().getCentroid().getCoordinate().y;
        double lngOffset = lng - recommendation.getRoute().getCentroid().getCoordinate().x;

        ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();
        for(Coordinate coordinate : recommendation.getRoute().getCoordinates()) {
            coordinateArrayList.add(new Coordinate(coordinate.getX() + lngOffset, coordinate.getY() + latOffset));
        }
        Coordinate[] coordinates = coordinateArrayList.toArray(Coordinate[]::new);
        Geometry modifiedRoute = new GeometryFactory().createLineString(coordinates);

        // then

        assertThat(modifiedRoute.toString()).isNotEqualTo(route.toString());
        assertThat(modifiedRoute.getCoordinate().getX()).isEqualTo(route.getCoordinate().getX()+(lng-route.getCentroid().getX()));
        assertThat(modifiedRoute.getCoordinate().getY()).isEqualTo(route.getCoordinate().getY()+(lat-route.getCentroid().getY()));
        assertThat(modifiedRoute.getCentroid().getX()).isCloseTo(lng, Percentage.withPercentage(0.00001));
        assertThat(modifiedRoute.getCentroid().getY()).isCloseTo(lat, Percentage.withPercentage(0.00001));
    }
}