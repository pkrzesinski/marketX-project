package com.project.marketx.feature.currencies.controller;

import com.project.marketx.feature.api.model.CurrencyExchange;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.feature.currencies.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("api/v1/currencies")
public class CurrencyController {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);

    private CurrencyService currencyService;

    @Autowired
    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping("/main")
    public String displayMainPage(ServletRequest request, Model model) {
        List<Currency> currencyList = currencyService.getListOfCurrencies();

        String fromCurrency = request.getParameter("fromCurrency");
        String toCurrency = request.getParameter("toCurrency");
        if (fromCurrency != null && toCurrency != null) {
            Optional<CurrencyExchange> currencyExchange = currencyService.getCurrencyRate(fromCurrency, toCurrency);
            currencyExchange.ifPresent(exchange -> model.addAttribute("rateModel"
                    , exchange.getRealtimeCurrencyExchangeRate().getExchangeRate()));
        }

        model.addAttribute("currencyModel", currencyList);
        return "mainView";
    }

    @PostMapping("/main")
    public void getCurrencyJson(@RequestParam("fromCurrency") String fromCurrency
            , @RequestParam("toCurrency") String toCurrency) {
    }
}
