package com.project.marketx;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import com.project.marketx.feature.currencies.service.CurrencyService;
import com.project.marketx.feature.trendline.TrendLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketXApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketXApplication.class, args);
    }
}
