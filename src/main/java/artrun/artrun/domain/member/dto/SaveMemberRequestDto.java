package artrun.artrun.domain.member.dto;

import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import lombok.*;

import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SaveMemberRequestDto {
    private String email;
    private String nickname;
    private String profileImg;
    private String gender;
    private short height;
    private short weight;
    private short age;

    @Builder
    public SaveMemberRequestDto(String email, String nickname, String profileImg, String gender, short height, short weight, short age) {
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

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
