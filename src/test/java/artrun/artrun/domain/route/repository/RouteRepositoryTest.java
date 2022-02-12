package artrun.artrun.domain.route.repository;

import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.route.domain.Route;
import artrun.artrun.global.util.wktToGeometry;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RouteRepositoryTest {

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
    private RouteRepository routeRepository;

    @DisplayName("getRoute 첫 페이지")
    @Test
    void getRoutesFirst() {
        // given
        Geometry route = wktToGeometry.wktToGeometry("LINESTRING (30 10, 10 30, 40 40)");
        Member member = Member.builder().id(1L).build();
        List<Route> routeList = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            routeList.add(Route.builder()
                    .id((long) i)
                    .member(member)
                    .targetRoute(route)
                    .runRoute(route)
                    .title("title" + i)
                    .distance(1000)
                    .time(3600)
                    .kcal(104)
                    .color("000000")
                    .thickness((byte) 3)
                    .isPublic(true)
                    .build());
        }
        routeRepository.saveAll(routeList);

        // when
        List<Route> resultRoutes = routeRepository.getPublicRoutes(null);

        // then
        assertThat(resultRoutes).hasSize(5);
        assertThat(resultRoutes.get(0).getTitle()).isEqualTo("title30");
        assertThat(resultRoutes.get(4).getTitle()).isEqualTo("title26");
    }

}