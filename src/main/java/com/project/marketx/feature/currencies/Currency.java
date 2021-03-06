package com.project.marketx.feature.currencies;

public class Currency {

    private final String code;
    private final String currency;

    Currency(String code, String currency) {
        this.code = code;
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "code='" + code + '\'' +
                ", currency='" + currency + '\'' +
                '}';
    }
}
