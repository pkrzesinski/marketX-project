package com.project.marketx;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.feature.currencies.service.CurrencyService;
import com.project.marketx.jsonconverter.Converter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MarketXApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketXApplication.class, args);

        CurrencyService currencyService = new CurrencyService(new Converter());
        List<Currency> currencyList = currencyService.getListOfCurrencies();

        AlphavantageAPI alphavantageAPI = new AlphavantageAPI();
        FXDaily fxDaily = alphavantageAPI.getHistoricalData("USD", "EUR");
    }
}
