package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendationRepository extends JpaRepository<Recommendation, Long>, RecommendtaionCustomRepository {
}
