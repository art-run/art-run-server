package artrun.artrun.domain.recommendation.service;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
import artrun.artrun.global.util.requester.OsrmRequester;
import org.locationtech.jts.geom.*;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    private final OsrmRequester osrmRequester;

    public RecommendationService(RecommendationRepository recommendationRepository, OsrmRequester osrmRequester) {
        this.recommendationRepository = recommendationRepository;
        this.osrmRequester = osrmRequester;
    }

    @Transactional(readOnly = true)
    public List<RecommendationResponseDto> getRecommendationsByDistance(int distance, Pageable pageable) {
        return recommendationRepository.getRecommendationsByDistance(distance, pageable)
                .stream()
                .map(RecommendationResponseDto::of)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecommendationResponseDto adjustRecommendationToCenter(Long recommendationId, Double lat, Double lng) {
        Recommendation recommendation = recommendationRepository.getById(recommendationId);

        // 1. centerPosition과 centroid의 차이만큼 평행이동
        double latOffset = lat - recommendation.getRoute().getCentroid().getCoordinate().y;
        double lngOffset = lng - recommendation.getRoute().getCentroid().getCoordinate().x;

        ArrayList<Coordinate> coordinateArrayList = new ArrayList<>();
        for(Coordinate coordinate : recommendation.getRoute().getCoordinates()) {
            coordinateArrayList.add(new Coordinate(coordinate.getX() + lngOffset, coordinate.getY() + latOffset));
        }
        Coordinate[] coordinates = coordinateArrayList.toArray(Coordinate[]::new);
        Geometry geometry = new GeometryFactory().createLineString(coordinates);

        // 2. OSRM API 기반 맵 매칭
        Geometry matchedRoute = osrmRequester.match(geometry);

        return RecommendationResponseDto.builder()
                .id(recommendation.getId())
                .wktRoute(matchedRoute.toString())
                .title(recommendation.getTitle())
                .distance(recommendation.getDistance())
                .build();
    }
}
