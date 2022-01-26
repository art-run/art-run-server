package artrun.artrun.domain.auth.controller;

import artrun.artrun.domain.auth.dto.AuthRequestDto;
import artrun.artrun.domain.auth.dto.AuthResponseDto;
import artrun.artrun.domain.auth.dto.TokenDto;
import artrun.artrun.domain.auth.dto.TokenRequestDto;
import artrun.artrun.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDto> signup(@RequestBody AuthRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.signup(memberRequestDto));
    }

    @GetMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody AuthRequestDto memberRequestDto) {
        return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
