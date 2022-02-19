package artrun.artrun.domain.recommendation.service;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getRecommendationsByDistance(int distance, Long lastRecommendationId) {
        return recommendationRepository.getRecommendationsByDistance(distance, lastRecommendationId);
    }

}
