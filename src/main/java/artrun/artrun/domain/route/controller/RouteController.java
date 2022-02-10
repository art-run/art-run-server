package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.*;
import artrun.artrun.domain.route.service.RouteFindService;
import artrun.artrun.domain.route.service.RouteRunService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class RouteController {

    private final RouteRunService routeRunService;

    private final RouteFindService routeFindService;

    @ApiOperation(value= "routeId로 Route 정보 조회")
    @GetMapping("/route/{routeId}")
    public ResponseEntity<RouteResponseDto> get(@PathVariable Long routeId) {
        return ResponseEntity.ok(routeFindService.get(routeId));
    }

    @ApiOperation(value="달리기 시작", notes = "목표 경로 그리기가 끝나고, 시작 버튼을 누를 때")
    @PostMapping("/route/start")
    public ResponseEntity<RouteStartResponseDto> start(@RequestBody RouteStartRequestDto routeStartRequestDto) {
        return ResponseEntity.ok(routeRunService.start(routeStartRequestDto));
    }

    @ApiOperation(value="달리기 종료", notes = "달리기 종료 및 기록카드 작성 후, 저장 버튼을 누를 때")
    @PostMapping("/route/finish")
    public ResponseEntity<RouteFinishResponseDto> finish(@RequestBody RouteFinishRequestDto routeFinishRequestDto) {
        return ResponseEntity.ok(routeRunService.finish(routeFinishRequestDto));
    }
}
