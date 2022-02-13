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
    @Column(name = "member_id", updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Authority authority;

    @Column(nullable = false, unique = true)
    private String nickname;

    private String profileImg;

    @Embedded
    private BodyInfo bodyInfo;

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
        if(Objects.nonNull(member.getBodyInfo())) {
            this.bodyInfo = member.getBodyInfo();
        }
    }
}
