package co.com.bancolombia.mscurrencytest.domain.model.entities;

import co.com.bancolombia.mscurrencytest.domain.model.constants.DatabaseNames;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;

import javax.persistence.*;
import java.util.Objects;

@Entity
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@NaturalIdCache
@Table(name = "currency", schema = DatabaseNames.MAIN_SCHEMA)
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "currency_seq")
    @SequenceGenerator(name = "currency_seq", allocationSize = 5)
    private Integer id;

    @NaturalId
    @Column(name = "api_identifier")
    private String apiIdentifier;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "status", nullable = false)
    private String status;
    @Column(name = "symbol", nullable = false)
    private String symbol;
    @Column(name = "type", nullable = false)
    private String type;
    @Column(name = "url", nullable = false)
    private String url;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Currency currency = (Currency) o;
        return id.equals(currency.id) && apiIdentifier.equals(currency.apiIdentifier) && name.equals(currency.name) && status
                .equals(currency.status) && symbol.equals(currency.symbol) && type.equals(currency.type) && url.equals(currency.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, apiIdentifier, name, status, symbol, type, url);
    }

    public Integer getId() {
        return id;
    }

    public Currency setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getApiIdentifier() {
        return apiIdentifier;
    }

    public Currency setApiIdentifier(String apiIdentifier) {
        this.apiIdentifier = apiIdentifier;
        return this;
    }

    public String getName() {
        return name;
    }

    public Currency setName(String name) {
        this.name = name;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Currency setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSymbol() {
        return symbol;
    }

    public Currency setSymbol(String symbol) {
        this.symbol = symbol;
        return this;
    }

    public String getType() {
        return type;
    }

    public Currency setType(String type) {
        this.type = type;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public Currency setUrl(String url) {
        this.url = url;
        return this;
    }
}
