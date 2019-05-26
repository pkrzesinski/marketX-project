package com.project.marketx.feature.api;

import com.project.marketx.feature.api.model.exchangerate.CurrencyExchange;
import com.project.marketx.feature.api.model.forexdailyprices.FXDaily;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AlphavantageAPITest {

    @Mock
    private RestTemplate restTemplate;
    @InjectMocks
    private AlphavantageAPI sut;

    @Test
    public void shouldTestIfCurrencyExchangeObjectIsReturned() {
        //given
        String API_KEY = "QKNXBJPRKN1DO9GN";
        String FUNCTION_RATE = "CURRENCY_EXCHANGE_RATE";
        String fromCurrency = "EUR";
        String toCurrency = "USD";
        String url = "https://www.alphavantage.co/query";
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("apikey", API_KEY)
                .queryParam("function", FUNCTION_RATE)
                .queryParam("from_currency", fromCurrency)
                .queryParam("to_currency", toCurrency)
                .build();
        when(restTemplate.getForObject(uriComponents.toUri(), CurrencyExchange.class))
                .thenReturn(new CurrencyExchange());

        //when
        CurrencyExchange result = sut.findExchangeRate(fromCurrency, toCurrency);

        //then
        assertThat(result).isExactlyInstanceOf(CurrencyExchange.class);
    }

    @Test
    public void shouldTestIfFXDailyObjectIsReturned() {
        //given
        String API_KEY = "QKNXBJPRKN1DO9GN";
        String FUNCTION_HISTORICAL = "FX_DAILY";
        String fromCurrency = "EUR";
        String toCurrency = "USD";
        String url = "https://www.alphavantage.co/query";
        UriComponents uriComponents = UriComponentsBuilder
                .fromUriString(url)
                .queryParam("function", FUNCTION_HISTORICAL)
                .queryParam("from_symbol", fromCurrency)
                .queryParam("to_symbol", toCurrency)
                .queryParam("apikey", API_KEY)
                .queryParam("outputsize", "full")
                .build();


        when(restTemplate.getForObject(uriComponents.toUri(), FXDaily.class)).thenReturn(new FXDaily());

        //when
        FXDaily result = sut.getHistoricalData(fromCurrency, toCurrency);

        //then
        assertThat(result).isExactlyInstanceOf(FXDaily.class);
    }

}