package com.project.marketx.feature.currencies.service;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import com.project.marketx.feature.currencies.controller.CurrencyController;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.jsonconverter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    private Converter converter;
    @Autowired
    private AlphavantageAPI alphavantageAPI;

    @PostConstruct
    public List<Currency> getListOfCurrencies() {
        LOG.info("List of currencies loaded");
        return getMapOfCurrencies().entrySet()
                .stream()
                .filter(Objects::nonNull)
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    public Optional<CurrencyExchange> getCurrencyRate(String fromCurrency, String toCurrency) {
        LOG.info("Currencies rate requested.");
        CurrencyExchange currencyExchange = alphavantageAPI.findExchangeRate(fromCurrency, toCurrency);
        return Optional.ofNullable(currencyExchange);
    }

    public FXDaily getHistoricalData(String fromCurrency, String toCurrency) {
        LOG.info("Historical date requested.");
        return alphavantageAPI.getHistoricalData(fromCurrency, toCurrency);
    }

    private Map<String, String> getMapOfCurrencies() {
        return converter.getCurrenciesMap();
    }
}
