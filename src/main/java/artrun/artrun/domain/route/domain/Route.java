package artrun.artrun.domain.route.domain;

import artrun.artrun.domain.BaseEntity;
import artrun.artrun.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "routes")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Route extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "route_id")
    private Long id;

    private Geometry targetRoute;

    private Geometry runRoute;

    private String title;

    private int distance; // 미터(m)

    private int time; // 초(sec)

    private int kcal;

    private String color; // Hex

    private Byte thickness;

    private Boolean isPublic;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
