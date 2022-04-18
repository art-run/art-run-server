package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.auth.exception.AuthorizationException;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.*;
import artrun.artrun.domain.route.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.operation.overlay.snap.LineStringSnapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static artrun.artrun.global.error.exception.ErrorCode.UNAUTHORIZED;

@Service
@Transactional
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    private final RecommendationRepository recommendationRepository;

    public RouteStartResponseDto startRoute(RouteStartRequestDto routeStartRequestDto) {
        SecurityUtil.isAuthorizedByMemberId(routeStartRequestDto.getMemberId());

        Route route = routeStartRequestDto.toRoute();
        return RouteStartResponseDto.of(routeRepository.save(route));
    }

    public RouteFinishResponseDto finishRoute(RouteFinishRequestDto routeFinishRequestDto) {
        SecurityUtil.isAuthorizedByMemberId(routeFinishRequestDto.getMemberId());

        // TODO worker 에 작업 위임
        recommendationRepository.save(routeFinishRequestDto.toRecommendation());

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

    public RouteMatchDto snapToTargetRoute(RouteMatchDto routeMatchDto) {
        Route route = routeRepository.getById(routeMatchDto.getRouteId());

        LineStringSnapper lineStringSnapper = new LineStringSnapper((LineString)route.getTargetRoute(), 0.05);
        Coordinate coordinate = new Coordinate(routeMatchDto.getLng(), routeMatchDto.getLat());
        Coordinate snappedCoordinate = lineStringSnapper.snapTo(new Coordinate[]{coordinate})[0];
        routeMatchDto.setLng(snappedCoordinate.getX());
        routeMatchDto.setLat(snappedCoordinate.getY());

        return routeMatchDto;
    }
}
