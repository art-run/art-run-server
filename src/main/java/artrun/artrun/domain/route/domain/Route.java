package artrun.artrun.domain.route.domain;

import artrun.artrun.domain.BaseEntity;
import artrun.artrun.domain.member.domain.Member;
import lombok.*;
import org.hibernate.annotations.Type;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;

import javax.persistence.*;

@Entity
@Table(name = "routes")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Route extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "route_id", updatable = false)
    private Long id;

    @Column(nullable = false)
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

    public void updateAtFinish(Route route) {
        this.runRoute = route.runRoute;
        this.title = route.title;
        this.distance = route.distance;
        this.time = route.time;
        this.kcal = route.kcal;
        this.color = route.color;
        this.thickness = route.thickness;
        this.isPublic = route.isPublic;
    }

    public Boolean isOwnedBy(Long memberId) {
        return this.member.getId() == memberId;
    }
}
