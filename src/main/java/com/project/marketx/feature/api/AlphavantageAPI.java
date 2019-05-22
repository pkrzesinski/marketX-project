package com.project.marketx.feature.api;

import org.springframework.stereotype.Component;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

@Component
public class AlphavantageAPI {
    private final String API_KEY = "QKNXBJPRKN1DO9GN";
    private final String FUNCTION = "CURRENCY_EXCHANGE_RATE";

    public Response findExchangeRate(String fromCurrency, String toCurrency) {

        String address = "https://www.alphavantage.co/query";

        Form form = new Form();
        form.param("apikey", API_KEY);
        form.param("function", FUNCTION);
        form.param("from_currency", fromCurrency);
        form.param("to_currency", toCurrency);

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(address);
        Response response = webTarget.request().post(Entity.form(form));

        return response;
    }
}
