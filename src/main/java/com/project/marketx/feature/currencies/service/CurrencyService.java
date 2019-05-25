package com.project.marketx.feature.currencies.service;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.jsonconverter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Startup
public class CurrencyService {

    @Autowired
    private Converter converter;
    @Autowired
    private AlphavantageAPI alphavantageAPI;

    @PostConstruct
    public List<Currency> getListOfCurrencies() {
        return getMapOfCurrencies().entrySet()
                .stream()
                .filter(Objects::nonNull)
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Optional<CurrencyExchange> getCurrencyRate(String fromCurrency, String toCurrency) {
        CurrencyExchange currencyExchange = alphavantageAPI.findExchangeRate(fromCurrency, toCurrency);
        return Optional.ofNullable(currencyExchange);
    }

    public FXDaily getHistoricalData(String fromCurrency, String toCurrency) {
        FXDaily fxDaily = alphavantageAPI.getHistoricalData(fromCurrency, toCurrency);
        return fxDaily;
    }


    private Map<String, String> getMapOfCurrencies() {
        return converter.getCurrenciesMap();
    }
}
