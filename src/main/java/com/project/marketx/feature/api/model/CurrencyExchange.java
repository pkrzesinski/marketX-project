package com.project.marketx.feature.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyExchange {

    @JsonProperty("Realtime Currency Exchange Rate")
    private RealTimeCurrencyExchange realtimeCurrencyExchangeRate;

    public CurrencyExchange() {
    }

    public RealTimeCurrencyExchange getRealtimeCurrencyExchangeRate() {
        return realtimeCurrencyExchangeRate;
    }

    public void setRealtimeCurrencyExchangeRate(RealTimeCurrencyExchange realtimeCurrencyExchangeRate) {
        this.realtimeCurrencyExchangeRate = realtimeCurrencyExchangeRate;
    }

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "realtimeCurrencyExchangeRate=" + realtimeCurrencyExchangeRate +
                '}';
    }
}
