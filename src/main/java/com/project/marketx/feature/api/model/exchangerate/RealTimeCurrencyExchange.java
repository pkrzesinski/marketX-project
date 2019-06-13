package com.project.marketx.feature.api.model.exchangerate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RealTimeCurrencyExchange {

    @JsonProperty("1. From_Currency Code")
    private String fromCurrencyCode;
    @JsonProperty("3. To_Currency Code")
    private String toCurrencyCode;
    @JsonProperty("5. Exchange Rate")
    private BigDecimal exchangeRate;
    @JsonProperty("6. Last Refreshed")
    private String lastRefreshed;
    @JsonProperty("7. Time Zone")
    private String timeZone;

    public RealTimeCurrencyExchange() {
    }

    public String getFromCurrencyCode() {
        return fromCurrencyCode;
    }

    public String getToCurrencyCode() {
        return toCurrencyCode;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public String getLastRefreshed() {
        return lastRefreshed;
    }

    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public String toString() {
        return "RealTimeCurrencyExchange{" +
                "fromCurrencyCode='" + fromCurrencyCode + '\'' +
                ", toCurrencyCode='" + toCurrencyCode + '\'' +
                ", exchangeRate=" + exchangeRate +
                ", lastRefreshed=" + lastRefreshed +
                ", timeZone='" + timeZone + '\'' +
                '}';
    }
}
