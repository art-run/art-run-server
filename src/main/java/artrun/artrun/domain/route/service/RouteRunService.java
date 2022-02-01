package artrun.artrun.domain.route.service;

import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteRunService {

    private final RouteRepository routeRepository;

    public Route start(Route route) {
        return routeRepository.save(route);
    }
}
