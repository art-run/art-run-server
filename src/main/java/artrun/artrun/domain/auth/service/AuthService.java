package artrun.artrun.domain.auth.service;

import artrun.artrun.domain.auth.TokenProvider;
import artrun.artrun.domain.auth.domain.RefreshToken;
import artrun.artrun.domain.auth.dto.*;
import artrun.artrun.domain.auth.exception.AuthenticationException;
import artrun.artrun.domain.auth.repository.RefreshTokenRepository;
import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static artrun.artrun.global.error.exception.ErrorCode.EMAIL_DUPLICATION;


@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    public SignupResponseDto signup(SignupRequestDto signupRequestDto) {
        if(memberRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new AuthenticationException(EMAIL_DUPLICATION);
        }

        Member member = signupRequestDto.toMember(passwordEncoder);
        return SignupResponseDto.of(memberRepository.save(member));
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto) {
        // 1. Login ID/PW 를 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        // 2. 토큰 검증
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        // 3. authentication 기반으로 JWT 토큰 생성
        TokenResponseDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 4. RefreshToken 저장
        RefreshToken refreshToken = RefreshToken.builder()
                .key(authentication.getName())
                .value(tokenDto.getRefreshToken())
                .build();

        refreshTokenRepository.save(refreshToken);

        // 5. AccessToken, RefreshToken 발급
        return tokenDto;
    }

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto) {
        // 1. RefreshToken 검증
        if (!tokenProvider.validateToken(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("Refresh Token이 유효하지 않습니다.");
        }

        // 2. Access Token에서 Member ID 가져오기
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        // 3. 저장소에서 Member ID를 기반으로 Refresh Token 값 가져옴
        RefreshToken refreshToken = refreshTokenRepository.findByKey(authentication.getName())
                .orElseThrow(() -> new RuntimeException("로그아웃된 사용자입니다."));

        // 4. Refresh Token 일치하는지 검사
        if (!refreshToken.getValue().equals(tokenRequestDto.getRefreshToken())) {
            throw new RuntimeException("토큰의 유저 정보가 일치하지 않습니다.");
        }

        // 5. 새로운 토큰 생성
        TokenResponseDto tokenDto = tokenProvider.generateTokenDto(authentication);

        // 6. 저장소에 Refresh Token 업데이트
        RefreshToken newRefreshToken = refreshToken.updateValue(tokenDto.getRefreshToken());
        refreshTokenRepository.save(newRefreshToken);

        // 7. AccessToken, RefreshToken 발급
        return tokenDto;
    }
}
