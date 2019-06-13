package com.project.marketx.feature.currencies;

import com.project.marketx.feature.api.AlphavantageAPI;
import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class CurrencyServiceMockitoTest {

    @Mock
    private AlphavantageAPI alphavantageAPI;
    @InjectMocks
    private CurrencyService mockedSut;

    @Test
    public void shouldTestIfAPIMethodIsCalledOnlyOnce() {
        //given
        when(alphavantageAPI.findExchangeRate(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockedCurrencyExchangeObject());

        //when
        mockedSut.getCurrencyRate(Mockito.anyString(), Mockito.anyString());

        //then
        verify(alphavantageAPI, times(1)).findExchangeRate(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void shouldTestIfOptionalIsNotEmpty() {
        //given
        when(alphavantageAPI.findExchangeRate(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockedCurrencyExchangeObject());

        //when
        Optional<CurrencyExchange> result = mockedSut.getCurrencyRate(Mockito.anyString(), Mockito.anyString());

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void shouldTestIfAPIForGetHistoricalDataIsCalledOnlyOnce() {
        //given
        when(alphavantageAPI.getHistoricalData(Mockito.anyString(), Mockito.anyString()))
                .thenReturn(mockedFXDailyObject());

        //when
        mockedSut.getHistoricalData(Mockito.anyString(), Mockito.anyString()).get();

        //then
        verify(alphavantageAPI, times(1)).getHistoricalData(Mockito.anyString(), Mockito.anyString());
    }

    private CurrencyExchange mockedCurrencyExchangeObject() {
        return new CurrencyExchange();
    }

    private FXDaily mockedFXDailyObject() {
        return new FXDaily();
    }
}
