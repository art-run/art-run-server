package artrun.artrun.domain.member.controller;

import artrun.artrun.domain.member.dto.MemberResponseDto;
import artrun.artrun.domain.member.dto.SaveMemberRequestDto;
import artrun.artrun.domain.member.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @ApiOperation(value= "멤버 정보 보기", notes = "본인만 확인 가능")
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> getMember(@PathVariable Long memberId) {
        return ResponseEntity.ok(memberService.getMember(memberId));
    }

    @ApiOperation(value = "멤버 정보 수정", notes = "본인만 수정 가능, 수정할 멤버 정보만 선택하여 넘기면 수정된 멤버 정보가 반환됨")
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberResponseDto> saveMember(@PathVariable Long memberId,
                                                            @RequestBody SaveMemberRequestDto saveMemberRequestDto) {
        return ResponseEntity.ok(memberService.saveMember(memberId, saveMemberRequestDto));
    }

    @ApiOperation(value = "멤버 탈퇴", notes = "본인만 탈퇴 가능, 탈퇴할 멤버 아이디 넘기면 성공 여부 반환됨")
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.noContent().build();
    }

}
