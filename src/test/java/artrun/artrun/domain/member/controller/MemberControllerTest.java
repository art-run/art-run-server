package artrun.artrun.domain.member.controller;

import artrun.artrun.domain.BaseTestController;
import artrun.artrun.domain.member.domain.BodyInfo;
import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.member.dto.MemberResponseDto;
import artrun.artrun.domain.member.dto.SaveMemberRequestDto;
import artrun.artrun.domain.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
class MemberControllerTest extends BaseTestController {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    MemberService memberService;

    @Test
    @DisplayName("이메일 중복 확인")
    void checkDuplicatedEmail() throws Exception {
        // given
        String email = "nnyy@gmail.com";

        // when

        // then
        mockMvc.perform(get("/member/duplication")
                        .param("email", email)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    @DisplayName("본인 멤버 정보 확인")
    void getMember() throws Exception {
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

        // when
        when(memberService.getMember(any())).thenReturn(MemberResponseDto.of(member));

        // then
        mockMvc.perform(get("/member/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("email").value(member.getEmail()))
                .andExpect(jsonPath("nickname").value(member.getNickname()))
                .andExpect(jsonPath("gender").value(member.getBodyInfo().getGender().toString()))
                .andExpect(jsonPath("height").value(String.valueOf(member.getBodyInfo().getHeight())))
                .andExpect(jsonPath("weight").value(String.valueOf(member.getBodyInfo().getWeight())))
                .andExpect(jsonPath("age").value(String.valueOf(member.getBodyInfo().getAge())))
                .andExpect(status().isOk());
    }

    @DisplayName("멤버 정보 수정 - 닉네임")
    @Test
    void saveMemberNickname() throws Exception {
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
                .nickname("newnew")
                .build();
        member.update(SaveMemberRequestDto.toMember(saveMemberRequestDto));
        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);

        // when
        when(memberService.saveMember(any(), any()))
                .thenReturn(memberResponseDto);
        // then
        String requestBody = objectMapper.writeValueAsString(saveMemberRequestDto);
        mockMvc.perform(put("/member/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("nickname").value(memberResponseDto.getNickname()))
                .andExpect(status().isOk());
    }

    @DisplayName("멤버 정보 수정 - 키,몸무게")
    @Test
    void saveMemberHeightWeight() throws Exception {
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
                .height((short) 189)
                .weight((short) 75)
                .build();
        member.update(SaveMemberRequestDto.toMember(saveMemberRequestDto));
        MemberResponseDto memberResponseDto = MemberResponseDto.of(member);

        // when
        when(memberService.saveMember(any(), any()))
                .thenReturn(memberResponseDto);
        // then
        String requestBody = objectMapper.writeValueAsString(saveMemberRequestDto);
        mockMvc.perform(put("/member/1")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf()))
                .andDo(print())
                .andExpect(jsonPath("height").value(String.valueOf(memberResponseDto.getHeight())))
                .andExpect(jsonPath("weight").value(String.valueOf(memberResponseDto.getWeight())))
                .andExpect(status().isOk());
    }

}