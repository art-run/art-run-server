package artrun.artrun.domain;

import artrun.artrun.domain.auth.TokenProvider;
import artrun.artrun.domain.auth.exception.JwtAccessDeniedHandler;
import artrun.artrun.domain.auth.exception.JwtAuthenticationEntryPoint;
import artrun.artrun.global.config.security.SecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

@WebMvcTest
@Import(SecurityConfig.class)
@ActiveProfiles("test")
public class BaseTestController {

    @MockBean
    TokenProvider tokenProvider;
    @MockBean
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    JwtAccessDeniedHandler jwtAccessDeniedHandler;

    public static ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("username", "password", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))));
    }
}
