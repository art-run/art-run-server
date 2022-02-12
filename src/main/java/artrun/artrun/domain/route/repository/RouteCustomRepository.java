package artrun.artrun.domain.route.repository;

import artrun.artrun.domain.route.domain.Route;

import java.util.List;

public interface RouteCustomRepository {
    List<Route> getPublicRoutes(Long lastRouteId);
    List<Route> getRoutesByMemberId(Long memberId, Long lastRouteId);
}
