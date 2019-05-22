package com.project.marketx;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.currencies.model.Currency;
import com.project.marketx.feature.currencies.service.CurrencyService;
import com.project.marketx.jsonconverter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class MarketXApplication {




    public static void main(String[] args) {
        SpringApplication.run(MarketXApplication.class, args);

        CurrencyService currencyService = new CurrencyService(new Converter());
        List<Currency> currencyList = currencyService.getListOfCurrencies();


    }
}
