package artrun.artrun.domain.route.service;

import artrun.artrun.domain.route.dto.RouteResponseDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteFindService {

    private final RouteRepository routeRepository;

    public RouteResponseDto get(Long routeId) {
        return RouteResponseDto.of(routeRepository.getById(routeId));
    }
}
