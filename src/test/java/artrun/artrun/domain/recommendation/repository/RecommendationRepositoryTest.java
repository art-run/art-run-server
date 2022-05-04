package artrun.artrun.domain.recommendation.repository;

import artrun.artrun.domain.recommendation.domain.Recommendation;
import artrun.artrun.global.util.wktToGeometry;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.EnabledIf;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

// TODO CI/CD 에서 테스트할 수 있도록 수정
@EnabledIf(expression = "#{environment.acceptsProfiles('local')}", loadContext = true)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RecommendationRepositoryTest {

    @TestConfiguration
    static class TestConfig {

        @PersistenceContext
        private EntityManager entityManager;

        @Bean
        public JPAQueryFactory jpaQueryFactory() {
            return new JPAQueryFactory(entityManager);
        }
    }

    @Autowired
    private RecommendationRepository recommendationRepository;

    @DisplayName("recommendation 조회 첫번째")
    @Test
    void getRecommendationsByDistanceFirst() {
        // given
        int distance = 1302;
        Geometry route = wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");

        List<Recommendation> recommendationList = new ArrayList<>();
        for(int i =1; i<= 30; i++) {
            recommendationList.add(Recommendation.builder()
                    .id((long) i)
                    .route(route)
                    .distance(100 * i)
                    .title("title" + i)
                    .usedCount((long) i)
                    .build());
        }
        recommendationRepository.saveAll(recommendationList);

        // when
        List<Recommendation> resultRecommendations = recommendationRepository.getRecommendationsByDistance(distance, Pageable.ofSize(5));

        // then
        assertThat(resultRecommendations).hasSize(5);
        assertThat(resultRecommendations.get(0).getUsedCount()).isGreaterThan(resultRecommendations.get(1).getUsedCount());
        assertThat(resultRecommendations.get(0).getUsedCount()).isEqualTo(19);
    }
}