package artrun.artrun.domain.route.repository;


import artrun.artrun.domain.route.domain.QRoute;
import artrun.artrun.domain.route.domain.Route;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static artrun.artrun.domain.route.domain.QRoute.route;

@Transactional(readOnly = true)
@Repository
public class RouteCustomRepositoryImpl implements RouteCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final int DEFAULT_PAGE_SIZE = 5;

    public RouteCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<Route> getPublicRoutes(Long lastRouteId) {
        final QRoute route = QRoute.route;

        return jpaQueryFactory.selectFrom(route)
                .innerJoin(route.member)
                .where(route.isPublic.eq(true))
                .where(ltRouteId(lastRouteId))
                .where(route.runRoute.isNotNull())
                .orderBy(route.id.desc())
                .limit(DEFAULT_PAGE_SIZE)
                .fetch();
    }

    @Override
    public List<Route> getRoutesByMemberId(Long memberId, Long lastRouteId) {
        final QRoute route = QRoute.route;

        return jpaQueryFactory.selectFrom(route)
                .innerJoin(route.member)
                .where(route.member.id.eq(memberId))
                .where(ltRouteId(lastRouteId))
                .where(route.runRoute.isNotNull())
                .orderBy(route.id.desc())
                .limit(DEFAULT_PAGE_SIZE)
                .fetch();
    }

    private BooleanExpression ltRouteId(Long routeId) {
        if (routeId == null) {
            return null; // BooleanExpression 자리에 null이 반환되면 조건문에서 자동으로 제거된다
        }
        return route.id.lt(routeId);
    }
}