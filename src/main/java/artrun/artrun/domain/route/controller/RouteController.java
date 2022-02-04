package artrun.artrun.domain.route.controller;

import artrun.artrun.domain.route.dto.*;
import artrun.artrun.domain.route.service.RouteFindService;
import artrun.artrun.domain.route.service.RouteRunService;
import lombok.AllArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/route")
@AllArgsConstructor
public class RouteController {

    private final RouteRunService routeRunService;

    private final RouteFindService routeFindService;

    @GetMapping("/{routeId}")
    public ResponseEntity<RouteResponseDto> get(@PathVariable Long routeId) {
        return ResponseEntity.ok(RouteResponseDto.of(routeFindService.get(routeId)));
    }

    @PostMapping("/start")
    public ResponseEntity<RouteStartResponseDto> start(@RequestBody RouteStartRequestDto routeStartRequestDto) throws ParseException {
        return ResponseEntity.ok(routeRunService.start(routeStartRequestDto));
    }

    @PostMapping("/finish")
    public ResponseEntity<RouteFinishResponseDto> finish(@RequestBody RouteFinishRequestDto routeFinishRequestDto) throws ParseException {
        return ResponseEntity.ok(routeRunService.finish(routeFinishRequestDto));
    }
}
