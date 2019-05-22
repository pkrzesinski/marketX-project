package com.project.marketx;

import com.project.marketx.jsonconverter.Converter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MarketXApplication {

    public static void main(String[] args) {
        SpringApplication.run(MarketXApplication.class, args);

        Converter converter = new Converter();
    }
}
