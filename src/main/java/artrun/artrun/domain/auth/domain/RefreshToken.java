package artrun.artrun.domain.auth.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "refresh_tokens")
@Entity
public class RefreshToken {

    @Id
    @Column(name = "token_key")
    private String key;

    @Column(name = "token_value")
    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}