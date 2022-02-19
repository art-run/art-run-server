package artrun.artrun.domain.recommendation.domain;

import artrun.artrun.domain.BaseEntity;
import lombok.*;
import org.locationtech.jts.geom.Geometry;

import javax.persistence.*;

@Entity
@Table(name = "recommendations")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Recommendation extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "recommendation_id", updatable = false)
    private Long id;

    @Column(nullable = false)
    private Geometry route;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int distance;

    private Long usedCount;

}
