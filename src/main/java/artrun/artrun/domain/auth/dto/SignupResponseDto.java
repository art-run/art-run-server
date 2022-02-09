package artrun.artrun.domain.auth.dto;

import artrun.artrun.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignupResponseDto {
    private String email;

    public static SignupResponseDto of(Member member) {
        return new SignupResponseDto(member.getEmail());
    }
}
