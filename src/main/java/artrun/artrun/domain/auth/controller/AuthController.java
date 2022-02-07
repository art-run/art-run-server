package artrun.artrun.domain.auth.controller;

import artrun.artrun.domain.auth.dto.AuthRequestDto;
import artrun.artrun.domain.auth.dto.AuthResponseDto;
import artrun.artrun.domain.auth.dto.TokenResponseDto;
import artrun.artrun.domain.auth.dto.TokenRequestDto;
import artrun.artrun.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody @Valid AuthRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody @Valid AuthRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponseDto> reissue(@RequestBody @Valid TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
