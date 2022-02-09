package artrun.artrun.domain.auth.controller;

import artrun.artrun.domain.BaseTestController;
import artrun.artrun.domain.auth.dto.LoginRequestDto;
import artrun.artrun.domain.auth.dto.SignupRequestDto;
import artrun.artrun.domain.auth.dto.SignupResponseDto;
import artrun.artrun.domain.auth.dto.TokenResponseDto;
import artrun.artrun.domain.auth.exception.AuthenticationException;
import artrun.artrun.domain.auth.service.AuthService;
import artrun.artrun.domain.auth.service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static artrun.artrun.global.error.exception.ErrorCode.EMAIL_DUPLICATION;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends BaseTestController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthService authService;

    @MockBean
    CustomUserDetailsService customUserDetailsService;

    LoginRequestDto loginRequestDto = new LoginRequestDto();

    @BeforeEach
    public void setup() {
        String email = "nnyy@gmail.com";
        String password = "password01";
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);
    }

    @Test
    @DisplayName("email과 password를 넘기면 회원가입을 한 뒤 성공하면 email을 반환한다.")
    void signupSuccess() throws Exception {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("nnyy@gmail.com")
                .password("password01")
                .nickname("nnyy")
                .gender("MALE")
                .height((short) 188)
                .weight((short) 77)
                .age((short) 20)
                .build();

        // when
        SignupResponseDto signupResponseDto = new SignupResponseDto(signupRequestDto.getEmail());
        when(authService.signup(any())).thenReturn(signupResponseDto);

        // then
        String requestBody = objectMapper.writeValueAsString(signupRequestDto);
        mockMvc.perform(post("/auth/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("email").value(loginRequestDto.getEmail()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("회원가입 시 email 형식이 틀리면 ErrorResponse 객체를 반환한다.")
    void singupEmailValid() throws Exception {
        // given
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        String email = "nnyy";
        String password = "password01";
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

        // when
        SignupResponseDto signupResponseDto = new SignupResponseDto(loginRequestDto.getEmail());
        when(authService.signup(any())).thenReturn(signupResponseDto);

        // then
        String requestBody = objectMapper.writeValueAsString(loginRequestDto);
        mockMvc.perform(post("/auth/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("code").value("C001"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("회원가입 시 email이 중복이면 ErrorResponse 객체를 반환한다.")
    void singupEmailDuplication() throws Exception {
        // given
        SignupRequestDto signupRequestDto = SignupRequestDto.builder()
                .email("nnyy@gmail.com")
                .password("password01")
                .nickname("nnyy")
                .gender("MALE")
                .height((short) 188)
                .weight((short) 77)
                .age((short) 20)
                .build();

        // when
        SignupResponseDto signupResponseDto = new SignupResponseDto(signupRequestDto.getEmail());
        when(authService.signup(any())).thenThrow(new AuthenticationException(EMAIL_DUPLICATION));

        // then
        String requestBody = objectMapper.writeValueAsString(signupRequestDto);
        mockMvc.perform(post("/auth/signup")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("code").value("A001"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("로그인을 성공하면 JWT 토큰을 반환한다.")
    void loginSuccess() throws Exception{
        // given
        String email = "nnyy@gmail.com";
        String password = "password01";
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setEmail(email);
        loginRequestDto.setPassword(password);

        // when
        TokenResponseDto tokenDto = TokenResponseDto.builder()
                .grantType("bearer")
                .accessToken("testAccessToken")
                .refreshToken("testRefreshToken")
                .accessTokenExpiresIn(1643557355750L)
                .build();
        when(authService.login(any())).thenReturn(tokenDto);

        // then
        String requestBody = objectMapper.writeValueAsString(loginRequestDto);
        mockMvc.perform(post("/auth/login")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("grantType").value(tokenDto.getGrantType()))
                .andExpect(jsonPath("accessToken").value(tokenDto.getAccessToken()))
                .andExpect(jsonPath("refreshToken").value(tokenDto.getRefreshToken()))
                .andExpect(jsonPath("accessTokenExpiresIn").value(tokenDto.getAccessTokenExpiresIn()))
                .andExpect(status().isOk());
    }

    @Test
    void reissue() {
    }
}