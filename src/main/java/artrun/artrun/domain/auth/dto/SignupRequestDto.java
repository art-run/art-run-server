package artrun.artrun.domain.auth.dto;

import artrun.artrun.domain.member.domain.Authority;
import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

@Getter
@Setter
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
    private int height;

    @Positive
    private int weight;

    @Positive
    private short age;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .gender(Gender.valueOf(gender))
                .height(height)
                .weight(weight)
                .age(age)
                .authority(Authority.ROLE_USER)
                .build();
    }

}
