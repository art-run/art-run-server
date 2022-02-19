package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.Recommendation;

import java.util.List;

public class RecommendationCustomRepositoryImpl implements RecommendtaionCustomRepository{
    @Override
    public List<Recommendation> getRecommendationsByDistance(int distance, Long lastRecommendationId) {
        return null;
    }
}
