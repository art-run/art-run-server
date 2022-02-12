package artrun.artrun.domain.member.domain;

import artrun.artrun.domain.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "members")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    private short height;

    private short weight;

    private short age;

    public void update(Member member) {
        if(Objects.nonNull(member.getEmail())) {
            this.email = member.getEmail();
        }
        if(Objects.nonNull(member.getNickname())) {
            this.nickname = member.getNickname();
        }
        if(Objects.nonNull(member.getProfileImg())) {
            this.profileImg = member.getProfileImg();
        }
        if(Objects.nonNull(member.getGender())) {
            this.gender = member.getGender();
        }
        if(member.getHeight() != 0) {
            this.height = member.getHeight();
        }
        if(member.getWeight() != 0) {
            this.weight = member.getWeight();
        }
        if(member.getAge() != 0) {
            this.age = member.getAge();
        }
    }
}
