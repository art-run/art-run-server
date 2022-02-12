package artrun.artrun.domain.member.domain;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BodyInfo {

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private short height;

    private short weight;

    private short age;

    @Builder
    public BodyInfo(Gender gender, short height, short weight, short age) {
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.age = age;
    }
}
