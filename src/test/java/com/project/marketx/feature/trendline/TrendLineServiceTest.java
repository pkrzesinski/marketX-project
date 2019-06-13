package com.project.marketx.feature.trendline;

import com.project.marketx.feature.api.model.forexdailyprices.DailyRate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrendLineServiceTest {

    @Autowired
    private TrendLineService sut;

    @Test
    public void shouldTestIfReturnedListSizeIsCorrect() {
        //given

        //when
        List<BigDecimal> result = sut.createTrendLine(mockedList());

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void shouldTestIfReturnedListHasCorrectValues() {
        //given

        //when
        List<BigDecimal> result = sut.createTrendLine(mockedList());

        //then
        assertEquals(0.9279, result.get(0).doubleValue(), 0.000001);
        assertEquals(0.9140, result.get(1).doubleValue(), 0.000001);
        assertEquals(0.9096, result.get(2).doubleValue(), 0.000001);

    }

    private Map<LocalDate, DailyRate> mockedList() {
        Map<LocalDate, DailyRate> createdMockedMap = new LinkedHashMap<>();
        createdMockedMap.put(LocalDate.of(2001, 3, 12), new DailyRate(new BigDecimal(0.9318),
                new BigDecimal(0.9342),
                new BigDecimal(0.9256),
                new BigDecimal(0.9279)));
        createdMockedMap.put(LocalDate.of(2001, 3, 13), new DailyRate(new BigDecimal(0.9274),
                new BigDecimal(0.9300),
                new BigDecimal(0.9124),
                new BigDecimal(0.9140)));
        createdMockedMap.put(LocalDate.of(2001, 3, 14), new DailyRate(new BigDecimal(0.9138),
                new BigDecimal(0.9209),
                new BigDecimal(0.9076),
                new BigDecimal(0.9096)));

        return createdMockedMap;
    }
}