package co.com.bancolombia.mscurrencytest.infrastructure.client.dto;

public class AbstractPercentChange {

    protected Double change24h;
    protected Double change7d;
    protected Double change30d;

    public AbstractPercentChange() {
        //serial
    }

    public Double getChange24h() {
        return change24h;
    }

    public AbstractPercentChange setChange24h(Double change24h) {
        this.change24h = change24h;
        return this;
    }

    public Double getChange7d() {
        return change7d;
    }

    public AbstractPercentChange setChange7d(Double change7d) {
        this.change7d = change7d;
        return this;
    }

    public Double getChange30d() {
        return change30d;
    }

    public AbstractPercentChange setChange30d(Double change30d) {
        this.change30d = change30d;
        return this;
    }
}
