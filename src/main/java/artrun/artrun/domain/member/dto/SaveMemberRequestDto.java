package artrun.artrun.domain.member.dto;

import artrun.artrun.domain.member.domain.BodyInfo;
import artrun.artrun.domain.member.domain.Gender;
import artrun.artrun.domain.member.domain.Member;
import lombok.*;

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
        Gender gender = saveMemberRequestDto.gender != null ? Gender.valueOf(saveMemberRequestDto.gender) : Gender.UNDEFINED;

        return Member.builder()
                .email(saveMemberRequestDto.email)
                .nickname(saveMemberRequestDto.nickname)
                .profileImg(saveMemberRequestDto.profileImg)
                .bodyInfo(BodyInfo.builder().
                        gender(gender)
                        .height(saveMemberRequestDto.height)
                        .weight(saveMemberRequestDto.weight)
                        .age(saveMemberRequestDto.age)
                        .build())
                .build();
    }
}
