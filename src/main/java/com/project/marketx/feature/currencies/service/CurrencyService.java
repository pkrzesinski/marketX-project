package com.project.marketx.feature.currencies.service;

import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.jsonconverter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CurrencyService {

    private Converter converter;

    @Autowired
    public CurrencyService(Converter converter) {
        this.converter = converter;
    }

    public List<Currency> getListOfCurrencies() {

        return getMapOfCurrencies().entrySet()
                .stream()
                .map(entry -> new Currency(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }

    private Map<String, String> getMapOfCurrencies() {
        return converter.getCurrenciesMap();
    }
}
