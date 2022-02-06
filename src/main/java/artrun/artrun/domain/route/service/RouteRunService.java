package artrun.artrun.domain.route.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.domain.route.dto.RouteFinishRequestDto;
import artrun.artrun.domain.route.dto.RouteFinishResponseDto;
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
        SecurityUtil.isAuthorizedByMemberId(routeStartRequestDto.getMemberId());

        Route route = routeStartRequestDto.toRoute();
        return RouteStartResponseDto.of(routeRepository.save(route));
    }

    public RouteFinishResponseDto finish(RouteFinishRequestDto routeFinishRequestDto) throws ParseException {
        SecurityUtil.isAuthorizedByMemberId(routeFinishRequestDto.getMemberId());

        Route route = routeRepository.getByIdAndMemberId(routeFinishRequestDto.getRouteId(), routeFinishRequestDto.getMemberId());
        route.updateAtFinish(routeFinishRequestDto.toRoute());

        return RouteFinishResponseDto.of(routeRepository.save(route));
    }
}
