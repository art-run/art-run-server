package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.QRecommendation;
import artrun.artrun.domain.recommendation.domain.Recommendation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static artrun.artrun.domain.recommendation.domain.QRecommendation.recommendation;
import static artrun.artrun.domain.route.domain.QRoute.route;

@Transactional(readOnly = true)
@Repository
public class RecommendationCustomRepositoryImpl implements RecommendationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    private final int DEFAULT_PAGE_SIZE = 5;

    public RecommendationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<Recommendation> getRecommendationsByDistance(int distance, Long lastRecommendationId) {
        final QRecommendation recommendation = QRecommendation.recommendation;

        return jpaQueryFactory.selectFrom(recommendation)
                .where(recommendation.distance.between(distance*0.5,distance*1.5))
                .where(ltRecommendationId(lastRecommendationId))
                .orderBy(recommendation.usedCount.desc(), recommendation.id.desc())
                .limit(DEFAULT_PAGE_SIZE)
                .fetch();
    }

    private BooleanExpression ltRecommendationId(Long recommendationId) {
        if (recommendationId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return recommendation.id.lt(recommendationId);
    }
}
