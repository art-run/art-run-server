package artrun.artrun.domain.auth.dto;

import artrun.artrun.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDto {
    private String email;

    public static AuthResponseDto of(Member member) {
        return new AuthResponseDto(member.getEmail());
    }
}
