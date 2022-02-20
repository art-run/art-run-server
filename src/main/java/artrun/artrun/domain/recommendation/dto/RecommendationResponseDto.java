package artrun.artrun.domain.recommendation.dto;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationResponseDto {
    private Long id;
    private String wktRoute;
    private String title;
    private int distance;

    public static RecommendationResponseDto of(Recommendation recommendation) {
        return RecommendationResponseDto.builder()
                .id(recommendation.getId())
                .wktRoute(recommendation.getRoute().toString())
                .title(recommendation.getTitle())
                .distance(recommendation.getDistance())
                .build();
    }
}
