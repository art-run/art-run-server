package artrun.artrun.domain.recommendation.controller;

import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.service.RecommendationService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecommendationController {

    private final RecommendationService recommendationService;

    @ApiOperation(value = "추천 경로들 보기", notes = "희망 거리(m)와 pageable을 넘기면 추천 경로 리스트 반환")
    @GetMapping("/recommendations")
    public ResponseEntity<List<RecommendationResponseDto>> getRecommendations(@RequestParam int distance, @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(recommendationService.getRecommendationsByDistance(distance, pageable));
    }

}
