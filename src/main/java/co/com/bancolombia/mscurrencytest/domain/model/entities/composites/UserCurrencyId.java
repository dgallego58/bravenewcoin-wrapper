package co.com.bancolombia.mscurrencytest.domain.model.entities.composites;

import co.com.bancolombia.mscurrencytest.domain.model.entities.Currency;
import co.com.bancolombia.mscurrencytest.domain.model.entities.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;


@Embeddable
public class UserCurrencyId implements Serializable {

    private Integer userId;
    private Integer currencyId;

    public UserCurrencyId() {
        //jpa requires for embedded objects
    }

    public UserCurrencyId(Integer userId, Integer currencyId) {
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
        return userId.equals(that.userId) && currencyId.equals(that.currencyId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, currencyId);
    }


    public Integer getUserId() {
        return userId;
    }

    public UserCurrencyId setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public Integer getCurrencyId() {
        return currencyId;
    }

    public UserCurrencyId setCurrencyId(Integer currencyId) {
        this.currencyId = currencyId;
        return this;
    }
}
