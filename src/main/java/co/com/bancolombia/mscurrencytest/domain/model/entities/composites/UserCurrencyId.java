package co.com.bancolombia.mscurrencytest.domain.model.entities.composites;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class UserCurrencyId implements Serializable {

    private Long userId;
    private Long currencyId;

    public UserCurrencyId() {
        //jpa stuff
    }

    public UserCurrencyId(Long userId, Long currencyId) {
        this.userId = userId;
        this.currencyId = currencyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserCurrencyId that = (UserCurrencyId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(currencyId, that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, currencyId);
    }

    public Long getUserId() {
        return userId;
    }

    public UserCurrencyId setUserId(Long userId) {
        this.userId = userId;
        return this;
    }

    public Long getCurrencyId() {
        return currencyId;
    }

    public UserCurrencyId setCurrencyId(Long currencyId) {
        this.currencyId = currencyId;
        return this;
    }
}
