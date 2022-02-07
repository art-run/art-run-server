package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.*;
import artrun.artrun.domain.route.service.RouteFindService;
import artrun.artrun.domain.route.service.RouteRunService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/route")
@AllArgsConstructor
public class RouteController {

    private final RouteRunService routeRunService;

    private final RouteFindService routeFindService;

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponseDto> get(@PathVariable @Valid Long routeId) {
        return ResponseEntity.ok(RouteResponseDto.of(routeFindService.get(routeId)));
    }

    @PostMapping("/start")
    public ResponseEntity<RouteStartResponseDto> start(@RequestBody @Valid RouteStartRequestDto routeStartRequestDto) {
        return ResponseEntity.ok(routeRunService.start(routeStartRequestDto));
    }

    @PostMapping("/finish")
    public ResponseEntity<RouteFinishResponseDto> finish(@RequestBody @Valid RouteFinishRequestDto routeFinishRequestDto) {
        return ResponseEntity.ok(routeRunService.finish(routeFinishRequestDto));
    }
}
