package com.project.marketx.feature.currencies;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.DailyRate;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import com.project.marketx.jsonconverter.Converter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.ejb.Startup;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Startup
public class CurrencyService {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyService.class);

    private Converter converter;
    private AlphavantageAPI alphavantageAPI;

    @Autowired
    public CurrencyService(Converter converter, AlphavantageAPI alphavantageAPI) {
        this.converter = converter;
        this.alphavantageAPI = alphavantageAPI;
    }

    @PostConstruct
    public List<Currency> getListOfCurrencies() {
        LOG.info("List of currencies loaded");
        return getMapOfCurrencies().entrySet()
                .stream()
                .filter(Objects::nonNull)
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    Optional<CurrencyExchange> getCurrencyRate(String fromCurrency, String toCurrency) {
        LOG.info("Currencies rate requested. Form currency {} to {}", fromCurrency, toCurrency);
        CurrencyExchange currencyExchange = alphavantageAPI.findExchangeRate(fromCurrency, toCurrency);
        return Optional.ofNullable(currencyExchange);
    }

    Optional<FXDaily> getHistoricalData(String fromCurrency, String toCurrency) {
        LOG.info("Historical currency rate data requested, from currency {} to {}", fromCurrency, toCurrency);
        FXDaily fxDaily = alphavantageAPI.getHistoricalData(fromCurrency, toCurrency);
        return Optional.ofNullable(fxDaily);
    }

    Optional<Map<LocalDate, DailyRate>> getHistoricalMap(String fromCurrency, String toCurrency) {
        LOG.info("Historical currency rate map requested, from currency {} to {}", fromCurrency, toCurrency);
        if (getHistoricalData(fromCurrency, toCurrency).isPresent()) {
            return Optional.ofNullable(getHistoricalData(fromCurrency, toCurrency).get().getTimeSeriesFX());
        }
        return Optional.empty();
    }

    private Map<String, String> getMapOfCurrencies() {
        return converter.getCurrenciesMap();
    }
}
