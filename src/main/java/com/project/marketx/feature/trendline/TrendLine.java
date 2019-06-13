package com.project.marketx.feature.trendline;

import com.project.marketx.feature.api.model.forexdailyprices.DailyRate;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class TrendLine {
    private static final int TIME_PERIOD_STEP_IN_DAYS = 45;

    private Map<LocalDate, BigDecimal> maxMap = new LinkedHashMap<>();
    private Map<LocalDate, BigDecimal> minMap = new LinkedHashMap<>();
    private List<Integer> regressionList = new ArrayList<>();

    @Autowired
    private TrendLine trendLine;

    public TrendLine() {
    }

    private void setMaxMap(Map<LocalDate, BigDecimal> maxMap) {
        this.maxMap = maxMap;
    }

    private void setMinMap(Map<LocalDate, BigDecimal> minMap) {
        this.minMap = minMap;
    }

    private void setRegressionList(List<Integer> regressionList) {
        this.regressionList = regressionList;
    }

    public List<BigDecimal> createTrendLine(Map<LocalDate, DailyRate> historicalExchangeRateMap) {

        List<BigDecimal> closePriceList = getClosePriceListForHistoricalData(historicalExchangeRateMap);

        List<LocalDate> closePriceDate = new ArrayList<>(historicalExchangeRateMap.keySet());

        LocalDate maxDate = null;
        LocalDate minDate = null;

        SimpleRegression simpleRegression = new SimpleRegression();

        int numberOfTimePeriods = closePriceList.size() / TIME_PERIOD_STEP_IN_DAYS;

        if (historicalExchangeRateMap.size() > 0) {
            minMap.clear();
            maxMap.clear();
            regressionList.clear();

            for (int i = 0; i < numberOfTimePeriods; i++) {

                int timePeriod = 0;
                simpleRegression.clear();

                BigDecimal max = BigDecimal.valueOf(Integer.MIN_VALUE);
                BigDecimal min = BigDecimal.valueOf(Integer.MAX_VALUE);

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

        return drawTrend(trendLine, historicalExchangeRateMap);
    }

    private List<BigDecimal> getClosePriceListForHistoricalData(Map<LocalDate, DailyRate> historicalExchangeRateMap) {
        return historicalExchangeRateMap.values()
                .stream()
                .map(DailyRate::getClose)
                .collect(Collectors.toList());
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

        if (map.size() > 0) {
            for (int i = 0; i < trendLine.regressionList.size(); i++) {

                int dateIndex = 0;

                if (trendLine.regressionList.get(i) == -1) {

                    LocalDate firstDate = minDateList.get(i);
                    int daysInBetween = 0;
                    BigDecimal step = new BigDecimal(0);
                    BigDecimal initialMinValue = minValueList.get(i);

                    for (int j = i + 1; j < regressionList.size() - 1; j++) {

                        if (regressionList.get(j) == -1 || regressionList.get(j + 1) == -1) {
                            daysInBetween = (int) Math.abs(DAYS.between(minDateList.get(j), firstDate));
                            step = (minValueList.get(j).subtract(initialMinValue)
                                    .divide(BigDecimal.valueOf(daysInBetween), 6));
                            i++;
                        } else {
                            break;
                        }
                    }

                    dateIndex = getDateIndex(map, dateIndex, firstDate);

                    for (int j = 0; j < daysInBetween; j++) {
                        trendList.set(dateIndex + j, initialMinValue.add(BigDecimal.valueOf(j).multiply(step)));
                    }

                } else if (trendLine.regressionList.get(i) == 1) {

                    LocalDate firstDate = maxDateList.get(i);
                    BigDecimal step = new BigDecimal(0);
                    int daysInBetween = 0;
                    BigDecimal initialMaxValue = maxValueList.get(i);

                    for (int j = i + 1; j < regressionList.size() - 1; j++) {

                        if (regressionList.get(j) == 1) {
                            daysInBetween = (int) Math.abs(DAYS.between(maxDateList.get(j), firstDate));
                            step = (maxValueList.get(j).subtract(initialMaxValue)
                                    .divide(BigDecimal.valueOf(daysInBetween), 6));
                            i++;
                        } else {
                            break;
                        }
                    }

                    dateIndex = getDateIndex(map, dateIndex, firstDate);

                    for (int j = 0; j < daysInBetween; j++) {
                        trendList.set(dateIndex + j, initialMaxValue.add(BigDecimal.valueOf(j).multiply(step)));
                    }
                }
            }
            return trendList;
        }
        return null;
    }

    private int getDateIndex(Map<LocalDate, DailyRate> map, int dateIndex, LocalDate firstDate) {
        for (LocalDate localDate : map.keySet()) {
            if (localDate.isEqual(firstDate)) {
                break;
            }
            dateIndex++;
        }
        return dateIndex;
    }
}