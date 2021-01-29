package co.com.bancolombia.mscurrencytest.domain.model.entities;

import co.com.bancolombia.mscurrencytest.domain.model.constants.DatabaseNames;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users", schema = DatabaseNames.MANAGER_SCHEMA)
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Column(name = "firstname")
    private String firstname;
    @Column(name = "lastname")
    private String lastname;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserCurrency> userCurrencies = new ArrayList<>();


    public void addCurrency(Currency currency, boolean favorite) {
        UserCurrency userCurrency = new UserCurrency(this, currency, favorite);
        userCurrencies.add(userCurrency);
    }

    public void removeCurrency(Currency currency) {
        for (UserCurrency userCurrency : this.getUserCurrencies()) {
            if (userCurrency.getUser().equals(this) && userCurrency.getCurrency().equals(currency)) {
                userCurrency.setUser(null).setCurrency(null);
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
        return Objects.equals(username, user.username) && Objects.equals(password, user.password) && Objects.equals(firstname, user.firstname) && Objects
                .equals(lastname, user.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, firstname, lastname);
    }

    public List<UserCurrency> getUserCurrencies() {
        return userCurrencies;
    }

    public User setUserCurrencies(List<UserCurrency> userCurrencies) {
        this.userCurrencies = userCurrencies;
        return this;
    }

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
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
