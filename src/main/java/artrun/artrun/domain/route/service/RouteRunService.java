package artrun.artrun.domain.route.service;

import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteStartResponseDto;
import artrun.artrun.domain.route.dto.RouteStartRequestDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteRunService {

    private final RouteRepository routeRepository;

    public RouteStartResponseDto start(RouteStartRequestDto routeStartRequestDto) throws ParseException {
        Route route = routeStartRequestDto.toRoute();
        return RouteStartResponseDto.of(routeRepository.save(route));
    }

    public Route finish(Route route) {
        return routeRepository.save(route);
    }
}
