package com.project.marketx.feature.currencies;

import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.DailyRate;
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
class CurrencyController {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);

    private CurrencyService currencyService;
    private TrendLine trendLine;

    @Autowired
    public CurrencyController(CurrencyService currencyService, TrendLine trendLine) {
        this.currencyService = currencyService;
        this.trendLine = trendLine;
    }

    @GetMapping
    public String displayMainPage(ServletRequest request, Model model) {

        List<Currency> currencyList = currencyService.getListOfCurrencies();
        String fromCurrency = request.getParameter("fromCurrency");
        String toCurrency = request.getParameter("toCurrency");

        if (fromCurrency != null && toCurrency != null) {
            LOG.info("Currency exchange from {} to {} displayed", fromCurrency, toCurrency);

            Optional<CurrencyExchange> currencyExchange = currencyService.getCurrencyRate(fromCurrency, toCurrency);
            Optional<Map<LocalDate, DailyRate>> historicalMap = currencyService.getHistoricalMap(fromCurrency, toCurrency);

            model.addAttribute("fromCurrencyModel", fromCurrency);
            model.addAttribute("toCurrencyModel", toCurrency);

            if (historicalMap.isPresent() && historicalMap.get().size() > 0) {
                currencyExchange.ifPresent(exchange -> {
                    model.addAttribute("rateModel", exchange.getRealtimeCurrencyExchangeRate()
                            .getExchangeRate());
                });
                model.addAttribute("historicalModel", changeKeyOrder(historicalMap.get()));

                List<BigDecimal> plotPoints = trendLine.createTrendLine(changeKeyOrder(historicalMap.get()));
                model.addAttribute("trendModel", plotPoints);
            } else {
                model.addAttribute("limitModel", "No data for selected currencies or " +
                        "limit of free requests has been exceeded." +
                        " Please try again in one minute.");
            }
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
