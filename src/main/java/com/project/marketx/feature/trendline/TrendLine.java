package com.project.marketx.feature.trendline;

import com.project.marketx.feature.api.model.forexdailyprices.DailyRate;
import com.project.marketx.feature.currencies.service.CurrencyService;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TrendLine {
    private static final BigDecimal TREND_LINE_PEAK = new BigDecimal(0.4);
    private static final int TIME_PERIOD_STEP_IN_DAYS = 30;

    @Autowired
    private CurrencyService currencyService;

    public void createTrendLine(String fromCurrency, String toCurrency) {
        Map<LocalDate, DailyRate> dataMap = changeKeyOrder(currencyService.getHistoricalData(fromCurrency, toCurrency)
                .getTimeSeriesFX());

//        BigDecimal sumClosePrice = dataMap.values()
//                .stream()
//                .map(DailyRate::getClose)
//                .reduce(BigDecimal::add).get();
//        BigDecimal averageClosePrice = sumClosePrice.divide(BigDecimal.valueOf(dataMap.values().size()));
//
//        dataMap.values()
//                .stream()
//                .map(DailyRate::getClose)
//                .filter(bigDecimal -> {
//                    if (bigDecimal.compareTo(averageClosePrice.add(averageClosePrice.multiply(TREND_LINE_PEAK))) >= 0 ||
//                            bigDecimal.compareTo(averageClosePrice.subtract(averageClosePrice.multiply(TREND_LINE_PEAK))) <= 0) {
//                    };
//                    return true;
//                }).collect(Collectors.toList());
//
//

        List<BigDecimal> closePriceList = dataMap.values()
                .stream()
                .map(DailyRate::getClose)
                .collect(Collectors.toList());

        List<LocalDate> closePriceDate = new ArrayList<>(dataMap.keySet());

        BigDecimal max = closePriceList.get(0);
        BigDecimal min = closePriceList.get(0);
        int timePeriod = 0;
        Map<LocalDate, BigDecimal> minMaxMap = new HashMap<>();

        SimpleRegression simpleRegression = new SimpleRegression();
        for (int i = 0; i < closePriceList.size() % TIME_PERIOD_STEP_IN_DAYS; i++) {

            for (int j = timePeriod; j < 30; j++) {

//                if (closePriceList.get(j).compareTo(min) < 0) {
//                    min = closePriceList.get(j);
//                }
//                if (closePriceList.get(j).compareTo(max) > 0) {
//                    max = closePriceList.get(j);
//                }
                simpleRegression.addData(closePriceList.get(j).doubleValue(), j);
            }
            double slope = simpleRegression.getSlope();
            timePeriod = timePeriod + TIME_PERIOD_STEP_IN_DAYS;
        }
    }

    private Map<LocalDate, DailyRate> changeKeyOrder(Map<LocalDate, DailyRate> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
