package artrun.artrun.domain.route.repository;

import artrun.artrun.domain.route.domain.Route;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;


import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RouteCustomRepositoryImplTest {

    @Autowired
    private RouteRepository routeRepository;

    @Test
    void findRecentRoutes() {
        // given
        Pageable pageable = PageRequest.of(1, 5);

        // when
        List<Route> routeList = routeRepository.findRecentRoutes(pageable);

        // then
        assertThat(routeList.size()).isLessThan(5);
    }
}