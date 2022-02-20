package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.QRecommendation;
import artrun.artrun.domain.recommendation.domain.Recommendation;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static artrun.artrun.domain.recommendation.domain.QRecommendation.recommendation;
import static artrun.artrun.domain.route.domain.QRoute.route;

@Transactional(readOnly = true)
@Repository
public class RecommendationCustomRepositoryImpl implements RecommendationCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public RecommendationCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<Recommendation> getRecommendationsByDistance(int distance, Pageable pageable) {
        final QRecommendation recommendation = QRecommendation.recommendation;

        return jpaQueryFactory.selectFrom(recommendation)
                .where(recommendation.distance.between(distance*0.5,distance*1.5))
                .orderBy(recommendation.usedCount.desc(), recommendation.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }
}
