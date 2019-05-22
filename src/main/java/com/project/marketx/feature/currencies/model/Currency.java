package com.project.marketx.feature.currencies.model;

public class Currency {

    private String code;
    private String currency;

    public Currency() {
    }

    public Currency(String code, String currency) {
        this.code = code;
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
