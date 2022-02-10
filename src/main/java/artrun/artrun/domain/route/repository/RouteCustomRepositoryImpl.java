package artrun.artrun.domain.route.repository;


import artrun.artrun.domain.route.domain.QRoute;
import artrun.artrun.domain.route.domain.Route;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static artrun.artrun.domain.route.domain.QRoute.route;

@Transactional(readOnly = true)
@Repository
public class RouteCustomRepositoryImpl implements RouteCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public RouteCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Route> findRecentRoutes(Pageable pageable) {
        final QRoute route = QRoute.route;

        return jpaQueryFactory.selectFrom(route)
                .innerJoin(route.member)
                .fetchJoin()
                .where(gtRouteId((long) pageable.getPageNumber()))
                .orderBy(route.id.desc())
                .limit(2)
                .fetch();
    }

    private BooleanExpression gtRouteId(Long routeId) {
        if (routeId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return route.id.gt(routeId);
    }
}