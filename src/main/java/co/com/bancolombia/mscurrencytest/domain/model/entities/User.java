package co.com.bancolombia.mscurrencytest.domain.model.entities;

import co.com.bancolombia.mscurrencytest.domain.model.constants.DatabaseNames;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
@Table(name = "user", schema = DatabaseNames.MAIN_SCHEMA)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usr_seq")
    @SequenceGenerator(name = "usr_seq", allocationSize = 5)
    private Integer id;

    @NaturalId
    @Column(name = "username", nullable = false, unique = true)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;

    private boolean active;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCurrency> userCurrencies = new ArrayList<>();

    public boolean isActive() {
        return active;
    }

    public User setActive(boolean active) {
        this.active = active;
        return this;
    }

    public void addCurrency(Currency currency, boolean favorite) {

        UserCurrency userCurrency = new UserCurrency(this, currency, favorite);
        this.getUserCurrencies().add(userCurrency);
    }

    public void removeCurrency(Currency currency) {
        for (UserCurrency userCurrency : this.getUserCurrencies()) {
            if (userCurrency.getUser().equals(this) && userCurrency.getCurrency().equals(currency)) {
                userCurrency.setUser(null).setUser(null);
                this.getUserCurrencies().remove(userCurrency);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return active == user.active && username.equals(user.username) && password.equals(user.password) && firstname.equals(user.firstname) && lastname
                .equals(user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstname, lastname, active);
    }

    public List<UserCurrency> getUserCurrencies() {
        return userCurrencies;
    }

    public User setUserCurrencies(List<UserCurrency> userCurrencies) {
        this.userCurrencies = userCurrencies;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstname() {
        return firstname;
    }

    public User setFirstname(String firstname) {
        this.firstname = firstname;
        return this;
    }

    public String getLastname() {
        return lastname;
    }

    public User setLastname(String lastname) {
        this.lastname = lastname;
        return this;
    }

}
