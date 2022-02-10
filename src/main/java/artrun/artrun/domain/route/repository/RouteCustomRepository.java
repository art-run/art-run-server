package artrun.artrun.domain.route.repository;

import artrun.artrun.domain.route.domain.Route;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RouteCustomRepository {
    List<Route> findRecentRoutes(Pageable pageable);
}
