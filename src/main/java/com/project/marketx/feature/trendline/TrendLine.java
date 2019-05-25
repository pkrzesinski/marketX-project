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

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class TrendLine {
    private static final BigDecimal TREND_LINE_PEAK = new BigDecimal(0.4);
    private static final int TIME_PERIOD_STEP_IN_DAYS = 30;

    private Map<LocalDate, BigDecimal> maxMap = new LinkedHashMap<>();
    private Map<LocalDate, BigDecimal> minMap = new LinkedHashMap<>();
    private List<Integer> regressionList = new ArrayList<>();

    @Autowired
    private CurrencyService currencyService;

    public Map<LocalDate, BigDecimal> getMaxMap() {
        return maxMap;
    }

    public void setMaxMap(Map<LocalDate, BigDecimal> maxMap) {
        this.maxMap = maxMap;
    }

    public Map<LocalDate, BigDecimal> getMinMap() {
        return minMap;
    }

    public void setMinMap(Map<LocalDate, BigDecimal> minMap) {
        this.minMap = minMap;
    }

    public List<Integer> getRegressionList() {
        return regressionList;
    }

    public void setRegressionList(List<Integer> regressionList) {
        this.regressionList = regressionList;
    }

    public List<BigDecimal> createTrendLine(String fromCurrency, String toCurrency, Map<LocalDate, DailyRate> map) {

        TrendLine trendLine = new TrendLine();

        List<BigDecimal> closePriceList = map.values()
                .stream()
                .map(DailyRate::getClose)
                .collect(Collectors.toList());

        List<LocalDate> closePriceDate = new ArrayList<>(map.keySet());

        LocalDate maxDate = null;
        LocalDate minDate = null;

        SimpleRegression simpleRegression = new SimpleRegression();

        int numberOfTimePeriods = closePriceList.size() / TIME_PERIOD_STEP_IN_DAYS;

        if (map.size() > 0) {
            minMap.clear();
            maxMap.clear();
            regressionList.clear();

            for (int i = 0; i < numberOfTimePeriods; i++) {

                int timePeriod = 0;
                simpleRegression.clear();

                BigDecimal max = BigDecimal.valueOf(Integer.MIN_VALUE);//closePriceList.get(TIME_PERIOD_STEP_IN_DAYS * i);
                BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);//closePriceList.get(TIME_PERIOD_STEP_IN_DAYS * i);

                for (int j = timePeriod; j < TIME_PERIOD_STEP_IN_DAYS; j++) {

                    int index = TIME_PERIOD_STEP_IN_DAYS * i + j;

                    if (closePriceList.get(index).compareTo(min) < 0) {
                        min = closePriceList.get(index);
                        minDate = closePriceDate.get(index);
                    }
                    if (closePriceList.get(index).compareTo(max) > 0) {
                        max = closePriceList.get(index);
                        maxDate = closePriceDate.get(index);
                    }

                    simpleRegression.addData(j, closePriceList.get(index).doubleValue());
                }
                double slope = simpleRegression.getSlope();
                maxMap.put(maxDate, max);
                minMap.put(minDate, min);

                if (simpleRegression.getSlope() > 0) {
                    regressionList.add(1);
                } else {
                    regressionList.add(-1);
                }
            }
        }

        trendLine.setMaxMap(maxMap);
        trendLine.setMinMap(minMap);
        trendLine.setRegressionList(regressionList);

        return drawTrend(trendLine, map);
    }

    private List<BigDecimal> drawTrend(TrendLine trendLine, Map<LocalDate, DailyRate> map) {
        List<BigDecimal> maxValueList = new ArrayList<>(maxMap.values());
        List<LocalDate> maxDateList = new ArrayList<>(maxMap.keySet());
        List<BigDecimal> minValueList = new ArrayList<>(minMap.values());
        List<LocalDate> minDateList = new ArrayList<>(minMap.keySet());

        List<BigDecimal> trendList = map.values()
                .stream()
                .map(DailyRate::getClose)
                .collect(Collectors.toList());

        Set<LocalDate> setDates = map.keySet();

        for (int i = 0; i < trendLine.regressionList.size(); i++) {

            int dateIndex = 0;
            int daysInBetween = (int) Math.abs(DAYS.between(maxDateList.get(i), minDateList.get(i)));
            BigDecimal step = (maxValueList.get(i).subtract(minValueList.get(i))
                    .divide(BigDecimal.valueOf(daysInBetween), 6));

            if (trendLine.regressionList.get(i) == -1) {
                step = step.multiply(BigDecimal.valueOf(regressionList.get(i)));

                LocalDate myDate = maxDateList.get(i);

                for (LocalDate localDate : map.keySet()) {
                    if (localDate.equals(myDate)) {
                        break;
                    }
                    dateIndex++;
                }

                for (int j = 0; j < daysInBetween; j++) {
                    trendList.set(dateIndex + j, maxValueList.get(i).add(BigDecimal.valueOf(j).multiply(step)));
                }

            } else if (trendLine.regressionList.get(i) == 1) {

                step = step.multiply(BigDecimal.valueOf(regressionList.get(i)));

                LocalDate myDate = minDateList.get(i);

                for (LocalDate localDate : map.keySet()) {
                    if (localDate.equals(myDate)) {
                        break;
                    }
                    dateIndex++;
                }

                for (int j = 0; j < daysInBetween; j++) {
                    trendList.set(dateIndex + j, minValueList.get(i).add(BigDecimal.valueOf(j).multiply(step)));
                }
            }
        }
        return trendList;
    }
}
