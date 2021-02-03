package co.com.bancolombia.mscurrencytest.domain.model.entities;

import co.com.bancolombia.mscurrencytest.domain.model.constants.DatabaseNames;
import co.com.bancolombia.mscurrencytest.domain.model.entities.composites.UserCurrencyId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "users_currencies", schema = DatabaseNames.MANAGER_SCHEMA)
public class UserCurrency implements Serializable {

    @EmbeddedId
    private UserCurrencyId userCurrencyId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("currencyId")
    @JoinColumn(name = "currency_id")
    private Currency currency;

    //extra field if is favorite
    @Column(name = "favorite")
    private boolean favorite;

    public UserCurrency() {
        //jpa auto match
    }

    public UserCurrency(User user, Currency currency, boolean favorite) {
        this.user = user;
        this.currency = currency;
        this.favorite = favorite;
        this.userCurrencyId = new UserCurrencyId(user.getId(), currency.getId());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserCurrency that = (UserCurrency) o;
        return Objects.equals(user, that.user) && Objects.equals(currency, that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, currency);
    }

    public UserCurrencyId getUserCurrencyId() {
        return userCurrencyId;
    }

    public UserCurrency setUserCurrencyId(UserCurrencyId userCurrencyId) {
        this.userCurrencyId = userCurrencyId;
        return this;
    }

    public User getUser() {
        return user;
    }

    public UserCurrency setUser(User user) {
        this.user = user;
        return this;
    }

    public Currency getCurrency() {
        return currency;
    }

    public UserCurrency setCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public UserCurrency setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }
}
