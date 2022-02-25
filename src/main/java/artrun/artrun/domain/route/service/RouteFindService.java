package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteCardResponseDto;
import artrun.artrun.domain.route.dto.RouteResponseDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RouteFindService {

    private final RouteRepository routeRepository;

    public RouteResponseDto get(Long routeId) {
        return RouteResponseDto.of(routeRepository.getById(routeId));
    }

    public List<RouteCardResponseDto> getPublicRoutes(Long lastRouteId) {
        List<Route> routeList = routeRepository.getPublicRoutes(lastRouteId);
        return routeList.stream().map(RouteCardResponseDto::of).collect(Collectors.toList());
    }

    public List<RouteCardResponseDto> getMyRoutes(Long lastRouteId) {
        Long memberId = SecurityUtil.getCurrentMemberId();
        List<Route> routeList = routeRepository.getRoutesByMemberId(memberId, lastRouteId);
        return routeList.stream().map(RouteCardResponseDto::of).collect(Collectors.toList());
    }
}
