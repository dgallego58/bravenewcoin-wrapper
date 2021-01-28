package co.com.bancolombia.mscurrencytest.domain.model.entities;

import co.com.bancolombia.mscurrencytest.domain.model.constants.DatabaseNames;
import co.com.bancolombia.mscurrencytest.domain.model.entities.composites.UserCurrencyId;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_currency", schema = DatabaseNames.MAIN_SCHEMA)
public class UserCurrency {

    @EmbeddedId
    private UserCurrencyId userCurrencyId;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_fk", foreignKey = @ForeignKey(name = "user_pk_key"))
    @MapsId("userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("currencyId")
    @JoinColumn(name = "currency_id_fk", foreignKey = @ForeignKey(name = "currency_pk_key"))
    private Currency currency;

    //extra field if is favorite
    @Column(name = "favorite", nullable = false)
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
        return favorite == that.favorite && userCurrencyId.equals(that.userCurrencyId) && user.equals(that.user) && currency
                .equals(that.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userCurrencyId, user, currency, favorite);
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
