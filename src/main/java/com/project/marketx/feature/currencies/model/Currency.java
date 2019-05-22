package com.project.marketx.feature.currencies.model;

import com.fasterxml.jackson.annotation.JsonGetter;

public class Currency {

    private String code;
    private String currency;

    public Currency() {
    }

    public Currency(String code, String currency) {
        this.code = code;
        this.currency = currency;
    }

    @JsonGetter("code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @JsonGetter("currency")
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
