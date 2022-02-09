package artrun.artrun.domain.member.dto;

import artrun.artrun.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberResponseDto {
    private String email;
    private String nickname;
    private String profileImg;
    private String gender;
    private short height;
    private short weight;
    private short age;

    public static MemberResponseDto of(Member member) {
        return MemberResponseDto.builder()
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImg(member.getProfileImg())
                .gender(member.getGender().toString())
                .height(member.getHeight())
                .weight(member.getWeight())
                .age(member.getAge())
                .build();
    }
}
