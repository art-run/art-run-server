package artrun.artrun.domain.auth.dto;

import artrun.artrun.domain.member.domain.Authority;
import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .profileImg(getRandomProfileImg())
                .gender(Gender.valueOf(gender))
                .height(height)
                .weight(weight)
                .age(age)
                .authority(Authority.ROLE_USER)
                .build();
    }

    private String getRandomProfileImg() {
        int value = ((int) (Math.random() * 10) % 9 + 1);
        return "https://randomuser.me/api/portraits/lego/" + value + ".jpg";
    }

}
