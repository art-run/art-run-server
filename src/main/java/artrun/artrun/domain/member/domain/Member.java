package artrun.artrun.domain.member.domain;

import artrun.artrun.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "members")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String nickname;

    private String profileImg;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int height;

    private int weight;

    private short age;
}
