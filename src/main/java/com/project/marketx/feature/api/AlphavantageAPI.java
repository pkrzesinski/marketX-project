package com.project.marketx.feature.api;

import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class AlphavantageAPI {
    private static final Logger LOG = LoggerFactory.getLogger(AlphavantageAPI.class);
    private final String API_KEY = "QKNXBJPRKN1DO9GN";
    private final String FUNCTION_RATE = "CURRENCY_EXCHANGE_RATE";
    private final String FUNCTION_HISTORICAL = "FX_DAILY";

    private String url = "https://www.alphavantage.co/query";

    private RestTemplate restTemplate = new RestTemplate();

    public CurrencyExchange findExchangeRate(String fromCurrency, String toCurrency) {

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("apikey", API_KEY)
                .queryParam("function", FUNCTION_RATE)
                .queryParam("from_currency", fromCurrency)
                .queryParam("to_currency", toCurrency)
                .build();

        return restTemplate.getForObject(uriComponents.toUri(), CurrencyExchange.class);
    }

    public FXDaily getHistoricalData(String fromCurrency, String toCurrency) {

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("function", FUNCTION_HISTORICAL)
                .queryParam("from_symbol", fromCurrency)
                .queryParam("to_symbol", toCurrency)
                .queryParam("apikey", API_KEY)
                .queryParam("outputsize", "full")
                .build();

        return restTemplate.getForObject(uriComponents.toUri(), FXDaily.class);
    }
}
