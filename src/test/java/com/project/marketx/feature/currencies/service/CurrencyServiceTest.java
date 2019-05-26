package com.project.marketx.feature.currencies.service;

import com.project.marketx.feature.currencies.model.Currency;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.not;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CurrencyServiceTest {

    @Autowired
    private CurrencyService sut;

    @Test
    public void shouldTestIfListOfCurrenciesIsNotNull() {
        //given

        //when
       List<Currency> result  = sut.getListOfCurrencies();

       //then
        assertThat(result).isNotNull();
    }

    @Test
    public void shouldTestIfListIsNotEmpty() {
        //given

        //when
        List<Currency> result = sut.getListOfCurrencies();

        //then
        assertThat(result).isNotEmpty();
    }

    @Test
    public void shouldTestIfListHasCorrectSize() {
        //given

        //when
        List<Currency> result = sut.getListOfCurrencies();

        //then
        assertThat(result).hasSize(171);
    }


}
