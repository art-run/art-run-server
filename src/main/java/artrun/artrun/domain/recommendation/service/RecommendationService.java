package artrun.artrun.domain.recommendation.service;

import artrun.artrun.domain.recommendation.dto.RecommendationResponseDto;
import artrun.artrun.domain.recommendation.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<RecommendationResponseDto> getRecommendationsByDistance(int distance, Pageable pageable) {
        return recommendationRepository.getRecommendationsByDistance(distance, pageable)
                .stream()
                .map(RecommendationResponseDto::of)
                .collect(Collectors.toList());
    }

}
