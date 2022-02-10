package artrun.artrun.domain.auth.controller;

import artrun.artrun.domain.auth.dto.*;
import artrun.artrun.domain.auth.exception.AuthenticationException;
import artrun.artrun.domain.auth.service.AuthService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static artrun.artrun.global.error.exception.ErrorCode.UNAUTHORIZED;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @ApiOperation(value= "회원가입")
    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        return ResponseEntity.ok(authService.signup(signupRequestDto));
    }

    @ApiOperation(value= "로그인")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid LoginRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @ApiOperation(value= "토큰 재발급")
    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }

    @GetMapping("/exception")
    public void exception() {
        throw new AuthenticationException(UNAUTHORIZED);
    }
}
