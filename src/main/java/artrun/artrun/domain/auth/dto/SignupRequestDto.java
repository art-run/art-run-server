package artrun.artrun.domain.auth.dto;

import artrun.artrun.domain.member.domain.Authority;
import artrun.artrun.domain.member.domain.BodyInfo;
import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SignupRequestDto {
    @Email
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String nickname;

    @NotEmpty
    private String gender;

    @Positive
    private short height;

    @Positive
    private short weight;

    @Positive
    private short age;

    @Builder
    public SignupRequestDto(String email, String password, String nickname, String gender, short height, short weight, short age) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .profileImg(getRandomProfileImg())
                .bodyInfo(BodyInfo.builder()
                        .gender(Gender.valueOf(gender))
                        .height(height)
                        .weight(weight)
                        .age(age)
                        .build())
                .authority(Authority.ROLE_USER)
                .build();
    }

    private String getRandomProfileImg() {
        int value = ((int) (Math.random() * 10) % 9 + 1);
        return "https://randomuser.me/api/portraits/lego/" + value + ".jpg";
    }

}
