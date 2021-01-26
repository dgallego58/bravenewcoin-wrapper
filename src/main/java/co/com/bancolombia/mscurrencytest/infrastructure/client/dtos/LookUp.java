package co.com.bancolombia.mscurrencytest.infrastructure.client.dtos;

public enum LookUp {
    MARKET("market"), ASSET("asset");

    private final String value;

    LookUp(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
