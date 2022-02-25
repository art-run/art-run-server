package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RecommendationCustomRepository {
    List<Recommendation> getRecommendationsByDistance(int distance, Pageable pageable);
}
