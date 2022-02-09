package artrun.artrun.domain.auth;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Slf4j
@NoArgsConstructor
public class SecurityUtil {
    public static Long getCurrentMemberId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new AuthenticationServiceException("Security Context에 인증 정보가 없습니다.");
        }

        return Long.parseLong(authentication.getName());
    }


    /**
     * 해당 memberId가 본인이 맞는지 token과 비교하여 검증
     * @param memberId
     */
    public static Boolean isAuthorizedByMemberId(Long memberId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new AuthenticationServiceException("Security Context에 인증 정보가 없습니다.");
        }

        if (Long.parseLong(authentication.getName()) != memberId) {
            throw new AuthenticationServiceException("토큰과 일치하지 않는 memberId입니다.");
        }

        return true;
    }
}
