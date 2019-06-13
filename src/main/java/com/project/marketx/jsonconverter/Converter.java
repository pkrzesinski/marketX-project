package com.project.marketx.jsonconverter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Objects;

@Component
public class Converter {

    private static final Logger LOG = LoggerFactory.getLogger(Converter.class);

    public Map<String, String> getCurrenciesMap() {

        ObjectMapper mapper = new ObjectMapper();

        try (InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader()
                .getResource("json/currencies.json")).openStream()) {
            return mapper.readValue(inputStream, new TypeReference<Map<String, String>>() {
            });
        } catch (IOException e) {
            LOG.warn("Json - currencies converter IO Exception caught " + e);
        }
        return null;
    }
}
