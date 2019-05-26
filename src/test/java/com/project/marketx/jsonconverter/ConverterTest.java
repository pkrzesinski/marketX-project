package com.project.marketx.jsonconverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ConverterTest {

    @Test
    public void shouldCheckIfMapSizeIsCorrect() {
        //given
        ObjectMapper sut = new ObjectMapper();

        //when
        Map<String, String> result = null;
        try {
            result = sut.readValue(mockedJson(), new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        assertThat(result).hasSize(3);
    }

    @Test
    public void shouldCheckIfMapHasProperKeys() {
        //given
        ObjectMapper sut = new ObjectMapper();

        //when
        Map<String, String> result = null;
        try {
            result = sut.readValue(mockedJson(), new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        //then
        assertThat(result).containsKeys("AED", "AFN", "ALL");
        assertThat(result).doesNotContainKeys("PLN", "EUR", "USD");
    }

    private InputStream mockedJson() {
        String testJson = "{\n" +
                "  \"AED\": \"United Arab Emirates Dirham\",\n" +
                "  \"AFN\": \"Afghan Afghani\",\n" +
                "  \"ALL\": \"Albanian Lek\"" +
                "}";
        return new ByteArrayInputStream(testJson.getBytes());
    }

    private Map<String, String> mockedJsonMap() {
        Map<String, String> createdMockedJson = new HashMap<>();
        createdMockedJson.put("AED", "United Arab Emirates Dirham");
        createdMockedJson.put("AFN", "Afghan Afghani");
        createdMockedJson.put("ALL", "Albanian Lek");
        return createdMockedJson;
    }
}
