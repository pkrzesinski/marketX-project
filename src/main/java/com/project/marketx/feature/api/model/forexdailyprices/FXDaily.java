package com.project.marketx.feature.api.model.forexdailyprices;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FXDaily {

    @JsonProperty("Time Series FX (Daily)")
    private Map<LocalDate, DailyRate> timeSeriesFX = new LinkedHashMap<>();

    public FXDaily() {
    }

    public Map<LocalDate, DailyRate> getTimeSeriesFX() {
        return timeSeriesFX;
    }

    @Override
    public String toString() {
        return "FXDaily{" +
                "timeSeriesFX=" + timeSeriesFX +
                '}';
    }
}
