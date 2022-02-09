package artrun.artrun.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class TokenRequestDto {
    @NotEmpty
    private String accessToken;
    @NotEmpty
    private String refreshToken;
}
