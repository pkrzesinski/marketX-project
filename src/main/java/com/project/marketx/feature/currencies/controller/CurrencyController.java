package com.project.marketx.feature.currencies.controller;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.feature.currencies.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.core.Response;
import java.util.List;

@Controller
@RequestMapping("api/v1/currencies")
public class CurrencyController {
    private static final Logger LOG = LoggerFactory.getLogger(CurrencyController.class);
    private CurrencyService currencyService;
    private AlphavantageAPI alphavantageAPI;

    @Autowired
    public CurrencyController(CurrencyService currencyService, AlphavantageAPI alphavantageAPI) {
        this.currencyService = currencyService;
        this.alphavantageAPI = alphavantageAPI;
    }

    @GetMapping("/main")
    public String displayMainPage(Model model) {

        List<Currency> currencyList = currencyService.getListOfCurrencies();

        model.addAttribute("currencyModel", currencyList);
        return "mainView";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST, produces = "application/json")
    public Response getCurrencyJson(@RequestParam("fromCurrent") String fromCurrency
            , @RequestParam("toCurrent") String toCurrency) {
        System.out.println(fromCurrency);
        System.out.println(toCurrency);
        alphavantageAPI.findExchangeRate(fromCurrency, toCurrency);
        return Response.ok().build();
    }

}
