package artrun.artrun.domain.route.repository;

import artrun.artrun.domain.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long>, RouteCustomRepository {
    Route getByIdAndMemberId(Long routeId, Long memberId);
}
