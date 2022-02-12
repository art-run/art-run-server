package artrun.artrun.domain.member.dto;

import artrun.artrun.domain.member.domain.Member;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberResponseDto {
    private String email;
    private String nickname;
    private String profileImg;
    private String gender;
    private short height;
    private short weight;
    private short age;

    @Builder
    public MemberResponseDto(String email, String nickname, String profileImg, String gender, short height, short weight, short age) {
        this.email = email;
        this.nickname = nickname;
        this.profileImg = profileImg;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }

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
