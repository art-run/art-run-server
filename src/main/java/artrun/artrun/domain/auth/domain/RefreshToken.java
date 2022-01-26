package artrun.artrun.domain.auth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refresh_tokens")
@Entity
public class RefreshToken {

    @Id
    private String key;

    private String value;

    public RefreshToken updateValue(String token) {
        this.value = token;
        return this;
    }
}