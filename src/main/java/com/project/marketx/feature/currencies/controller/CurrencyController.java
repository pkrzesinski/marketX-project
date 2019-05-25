package com.project.marketx.feature.currencies.controller;

import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.DailyRate;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.feature.currencies.service.CurrencyService;
import com.project.marketx.feature.trendline.TrendLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class CurrencyController {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);

    @Autowired
    private CurrencyService currencyService;
    @Autowired
    private TrendLine trendLine;
//
//    @Autowired
//    public CurrencyController(CurrencyService currencyService) {
//        this.currencyService = currencyService;
//    }

    @GetMapping
    public String displayMainPage(ServletRequest request, Model model) {
        List<Currency> currencyList = currencyService.getListOfCurrencies();
        String fromCurrency = request.getParameter("fromCurrency");
        String toCurrency = request.getParameter("toCurrency");

        if (fromCurrency != null && toCurrency != null) {

            Optional<CurrencyExchange> currencyExchange = currencyService.getCurrencyRate(fromCurrency, toCurrency);

            Map<LocalDate, DailyRate> historicalMap = currencyService.getHistoricalData(fromCurrency, toCurrency)
                    .getTimeSeriesFX();

            List<BigDecimal> value = trendLine.createTrendLine(fromCurrency, toCurrency, changeKeyOrder(historicalMap));

            model.addAttribute("trendModel", value);
            model.addAttribute("historicalModel", changeKeyOrder(historicalMap));
            model.addAttribute("fromCurrencyModel", fromCurrency);
            model.addAttribute("toCurrencyModel", toCurrency);
            currencyExchange.ifPresent(exchange -> model.addAttribute("rateModel"
                    , exchange.getRealtimeCurrencyExchangeRate().getExchangeRate()));
        }

        model.addAttribute("currencyModel", currencyList);
        return "mainView";
    }

    private Map<LocalDate, DailyRate> changeKeyOrder(Map<LocalDate, DailyRate> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
