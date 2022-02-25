package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.auth.exception.AuthorizationException;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteFinishRequestDto;
import artrun.artrun.domain.route.dto.RouteFinishResponseDto;
import artrun.artrun.domain.route.dto.RouteStartResponseDto;
import artrun.artrun.domain.route.dto.RouteStartRequestDto;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static artrun.artrun.global.error.exception.ErrorCode.UNAUTHORIZED;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteStartResponseDto startRoute(RouteStartRequestDto routeStartRequestDto) {
        SecurityUtil.isAuthorizedByMemberId(routeStartRequestDto.getMemberId());

        Route route = routeStartRequestDto.toRoute();
        return RouteStartResponseDto.of(routeRepository.save(route));
    }

    public RouteFinishResponseDto finishRoute(RouteFinishRequestDto routeFinishRequestDto) {
        SecurityUtil.isAuthorizedByMemberId(routeFinishRequestDto.getMemberId());

        Route route = routeRepository.getByIdAndMemberId(routeFinishRequestDto.getRouteId(), routeFinishRequestDto.getMemberId());
        route.updateAtFinish(routeFinishRequestDto.toRoute());

        return RouteFinishResponseDto.of(route);
    }

    public void deleteRoute(Long routeId) {
        Route route = routeRepository.getById(routeId);
        if(!route.isOwnedBy(SecurityUtil.getCurrentMemberId())) {
            throw new AuthorizationException("해당 경로 작성자와 일치하지 않는 memberId입니다.", UNAUTHORIZED);
        }
        routeRepository.delete(route);
    }
}
