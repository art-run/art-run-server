package artrun.artrun.domain.member.dto;

import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import lombok.*;

import java.util.Objects;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMemberRequestDto {
    private String email;
    private String nickname;
    private String profileImg;
    private String gender;
    private short height;
    private short weight;
    private short age;

    public static Member toMember(SaveMemberRequestDto saveMemberRequestDto) {
        Member.MemberBuilder memberBuilder = Member.builder()
                .email(saveMemberRequestDto.email)
                .nickname(saveMemberRequestDto.nickname)
                .profileImg(saveMemberRequestDto.profileImg);
        if (Objects.nonNull(saveMemberRequestDto.gender)) {
            memberBuilder.gender(Gender.valueOf(saveMemberRequestDto.gender));
        }
        return memberBuilder.height(saveMemberRequestDto.height)
                .weight(saveMemberRequestDto.weight)
                .age(saveMemberRequestDto.age)
                .build();
    }
}
