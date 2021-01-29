package co.com.bancolombia.mscurrencytest.domain.model.entities;

import co.com.bancolombia.mscurrencytest.domain.model.constants.DatabaseNames;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "currencies", schema = DatabaseNames.CRYPTO_SCHEMA)
public class Currency implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NaturalId
    @Column(name = "asset_id")
    private String assetId;
    @Column(name = "name")
    private String name;
    @Column(name = "status")
    private String status;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "currency_type")
    private String type;
    @Column(name = "price")
    private BigDecimal price;

    public Currency() {
        //jpa mapper
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Currency setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Currency currency = (Currency) o;
        return Objects.equals(assetId, currency.assetId) && Objects.equals(name, currency.name) && Objects.equals(status, currency.status) && Objects
                .equals(symbol, currency.symbol) && Objects.equals(type, currency.type) && Objects.equals(price, currency.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assetId, name, status, symbol, type, price);
    }

    public Long getId() {
        return id;
    }

    public Currency setId(Long id) {
        this.id = id;
        return this;
    }

    public String getAssetId() {
        return assetId;
    }

    public Currency setAssetId(String apiIdentifier) {
        this.assetId = apiIdentifier;
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
}
