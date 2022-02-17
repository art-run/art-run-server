package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.*;
import artrun.artrun.domain.route.service.RouteFindService;
import artrun.artrun.domain.route.service.RouteService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class RouteController {

    private final RouteService routeService;

    private final RouteFindService routeFindService;

    @ApiOperation(value= "routeId로 Route 정보 조회")
    @GetMapping("/route/{routeId}")
    public ResponseEntity<RouteResponseDto> get(@PathVariable Long routeId) {
        return ResponseEntity.ok(routeFindService.get(routeId));
    }

    @ApiOperation(value= "routeId로 Route 삭제")
    @DeleteMapping("/route/{routeId}")
    public ResponseEntity<Void> delete(@PathVariable Long routeId) {
        routeService.deleteRoute(routeId);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value="달리기 시작", notes = "목표 경로 그리기가 끝나고, 시작 버튼을 누를 때")
    @PostMapping("/route/start")
    public ResponseEntity<RouteStartResponseDto> start(@RequestBody RouteStartRequestDto routeStartRequestDto) {
        return ResponseEntity.ok(routeService.startRoute(routeStartRequestDto));
    }

    @ApiOperation(value="달리기 종료", notes = "달리기 종료 및 기록카드 작성 후, 저장 버튼을 누를 때")
    @PostMapping("/route/finish")
    public ResponseEntity<RouteFinishResponseDto> finish(@RequestBody RouteFinishRequestDto routeFinishRequestDto) {
        return ResponseEntity.ok(routeService.finishRoute(routeFinishRequestDto));
    }

    @ApiOperation(value="소셜 - 최신기록", notes="소셜 피드에서 Routes를 반환함 (정렬 기준: 최신순)")
    @GetMapping("/routes")
    public ResponseEntity<List<RouteCardResponseDto>> getRoutes(@RequestParam(required = false) Long lastRouteId) {
        return ResponseEntity.ok(routeFindService.getPublicRoutes(lastRouteId));
    }

    @ApiOperation(value="소셜 - 내 기록", notes="소셜 피드에서 내 Routes를 반환함 (정렬 기준: 최신순)")
    @GetMapping("/routes/me")
    public ResponseEntity<List<RouteCardResponseDto>> getMyRoutes(@RequestParam(required = false) Long lastRouteId) {
        return ResponseEntity.ok(routeFindService.getMyRoutes(lastRouteId));
    }
}
