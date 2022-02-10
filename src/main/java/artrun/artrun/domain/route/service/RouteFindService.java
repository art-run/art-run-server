package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteCardResponseDto;
import artrun.artrun.domain.route.dto.RouteResponseDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class RouteFindService {

    private final RouteRepository routeRepository;

    public RouteResponseDto get(Long routeId) {
        return RouteResponseDto.of(routeRepository.getById(routeId));
    }

    public List<RouteCardResponseDto> getRoutes(Long lastRouteId, Pageable pageable) {
        List<Route> routeList = routeRepository.getRoutes(lastRouteId, pageable);
        return routeList.stream().map(RouteCardResponseDto::of).collect(Collectors.toList());
    }

    public List<RouteCardResponseDto> getMyRoutes(Long lastRouteId, Pageable pageable) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<Route> routeList = routeRepository.getRoutesByMemberId(memberId, lastRouteId, pageable);
        return routeList.stream().map(RouteCardResponseDto::of).collect(Collectors.toList());
    }
}
