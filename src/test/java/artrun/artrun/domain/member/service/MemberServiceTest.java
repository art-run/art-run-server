package artrun.artrun.domain.member.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.auth.exception.AuthenticationException;
import artrun.artrun.domain.member.domain.BodyInfo;
import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.member.dto.MemberResponseDto;
import artrun.artrun.domain.member.dto.SaveMemberRequestDto;
import artrun.artrun.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @InjectMocks
    MemberService memberService;

    @Mock
    MemberRepository memberRepository;

    private static MockedStatic<SecurityUtil> securityUtilMockedStatic;

    @BeforeAll
    public static void beforeAll() {
        securityUtilMockedStatic = mockStatic(SecurityUtil.class);
    }

    @AfterAll
    public static void afterAll() {
        securityUtilMockedStatic.close();
    }

    @Test
    @DisplayName("멤버 조회: 정상 조회")
    void getMember() {
        // given
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .email("nnyy@gmail.com")
                .password("password01")
                .nickname("nnyy")
                .bodyInfo(BodyInfo.builder()
                        .gender(Gender.valueOf("MALE"))
                        .height((short) 188)
                        .weight((short) 77)
                        .age((short) 20)
                        .build())
                .build();

        // when
        when(SecurityUtil.isAuthorizedByMemberId(any())).thenReturn(true);
        when(memberRepository.getById(any())).thenReturn(member);

        // then
        assertThat(memberService.getMember(memberId).getNickname()).isEqualTo(MemberResponseDto.of(member).getNickname());
    }

    @Test
    @DisplayName("멤버 정보 닉네임을 수정함")
    void saveMemberNickname() {
        // given
        Member member = Member.builder()
                .email("nnyy@gmail.com")
                .password("password01")
                .nickname("nnyy")
                .bodyInfo(BodyInfo.builder()
                        .gender(Gender.valueOf("MALE"))
                        .height((short) 188)
                        .weight((short) 77)
                        .age((short) 20)
                        .build())
                .build();

        SaveMemberRequestDto saveMemberRequestDto = SaveMemberRequestDto.builder()
                .nickname("nnyy2")
                .build();

        // when
        when(SecurityUtil.isAuthorizedByMemberId(any())).thenReturn(true);
        when(memberRepository.getById(any())).thenReturn(member);
        MemberResponseDto memberResponseDto = memberService.saveMember(member.getId(), saveMemberRequestDto);

        // then
        assertThat(memberResponseDto.getNickname()).isEqualTo(saveMemberRequestDto.getNickname());
    }

    @Test
    @DisplayName("중복 이메일 체크: 중복 발생")
    void checkDuplicatedEmail() {
        // given
        String email = "nnyy@gmail.com";

        // when
        when(memberRepository.existsByEmail(any())).thenReturn(true);

        // then
        assertThrows(AuthenticationException.class, () -> memberService.checkDuplicatedEmail(email));
    }
}