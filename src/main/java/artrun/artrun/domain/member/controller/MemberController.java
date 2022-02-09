package artrun.artrun.domain.member.controller;

import artrun.artrun.domain.member.dto.MemberResponseDto;
import artrun.artrun.domain.member.dto.SaveMemberRequestDto;
import artrun.artrun.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 멤버 정보 보기 (본인만)
     * @param memberId
     * @return
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    /**
     * 멤버 정보 수정 (본인만)
     * @param memberId
     * @param saveMemberRequestDto
     * @return
     */
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> saveMember(@PathVariable Long memberId,
                                                            @RequestBody SaveMemberRequestDto saveMemberRequestDto) {
        return ResponseEntity.ok(memberService.saveMember(memberId, saveMemberRequestDto));
    }

}
