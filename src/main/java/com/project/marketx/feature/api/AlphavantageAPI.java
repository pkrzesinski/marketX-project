package com.project.marketx.feature.api;

import com.project.marketx.feature.api.model.CurrencyExchange;
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
    private final String FUNCTION = "CURRENCY_EXCHANGE_RATE";

    public CurrencyExchange findExchangeRate(String fromCurrency, String toCurrency) {

        String url = "https://www.alphavantage.co/query";

        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("apikey", API_KEY)
                .queryParam("function", FUNCTION)
                .queryParam("from_currency", fromCurrency)
                .queryParam("to_currency", toCurrency)
                .build();

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(uriComponents.toUri(), CurrencyExchange.class);
    }
}
