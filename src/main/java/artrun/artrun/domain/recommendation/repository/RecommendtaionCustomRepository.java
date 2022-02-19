package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.Recommendation;

import java.util.List;

public interface RecommendtaionCustomRepository {
    List<Recommendation> getRecommendationsByDistance(int distance, Long lastRecommendationId);
}
