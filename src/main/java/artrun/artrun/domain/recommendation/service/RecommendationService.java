package artrun.artrun.domain.recommendation.service;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

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

        for(Coordinate coordinate : recommendation.getRoute().getCoordinates()) {
            coordinate.setX(coordinate.getX() + lngOffset);
            coordinate.setY(coordinate.getY() + latOffset);
        }

        // TODO apply OSRM nearest

        return RecommendationResponseDto.of(recommendation);
    }
}
