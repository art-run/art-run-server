package artrun.artrun.domain.route.domain;

import artrun.artrun.domain.BaseEntity;
import artrun.artrun.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    private String targetRoute;

    private String runRoute;

    private String title;

    private int distance;

    private int time;

    private Short kcal;

    private String color;

    private Byte thickness;

    private Byte isPublic;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
