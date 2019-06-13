package com.project.marketx.feature.api.model.forexdailyprices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DailyRate {

    @JsonProperty("1. open")
    private BigDecimal open;
    @JsonProperty("2. high")
    private BigDecimal high;
    @JsonProperty("3. low")
    private BigDecimal low;
    @JsonProperty("4. close")
    private BigDecimal close;

    public DailyRate() {
    }

    public DailyRate(BigDecimal open, BigDecimal high, BigDecimal low, BigDecimal close) {
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
    }

    public BigDecimal getOpen() {
        return open;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getClose() {
        return close;
    }

    @Override
    public String toString() {
        return "DailyRate{" +
                "open=" + open +
                ", high=" + high +
                ", low=" + low +
                ", close=" + close +
                '}';
    }
}
