package artrun.artrun.domain.member.service;

import artrun.artrun.domain.auth.SecurityUtil;
import artrun.artrun.domain.auth.exception.AuthenticationException;
import artrun.artrun.domain.member.domain.Member;
import artrun.artrun.domain.member.dto.MemberResponseDto;
import artrun.artrun.domain.member.dto.SaveMemberRequestDto;
import artrun.artrun.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static artrun.artrun.global.error.exception.ErrorCode.EMAIL_DUPLICATION;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public MemberResponseDto getMember(Long memberId) {
        SecurityUtil.isAuthorizedByMemberId(memberId);
        return MemberResponseDto.of(memberRepository.getById(memberId));
    }

    public MemberResponseDto saveMember(Long memberId, SaveMemberRequestDto saveMemberRequestDto) {
        SecurityUtil.isAuthorizedByMemberId(memberId);
        Member member = memberRepository.getById(memberId);
        member.update(SaveMemberRequestDto.toMember(saveMemberRequestDto));
        return MemberResponseDto.of(member);
    }

    public void deleteMember(Long memberId) {
        SecurityUtil.isAuthorizedByMemberId(memberId);
        memberRepository.deleteById(memberId);
    }

    public void checkDuplicatedEmail(String email) {
        if(memberRepository.existsByEmail(email)) {
            throw new AuthenticationException(EMAIL_DUPLICATION);
        }
    }
}
