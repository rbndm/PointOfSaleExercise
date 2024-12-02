package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class BarcodePriceSenderService {

    private static final String DISPLAY_SERVICE_URL = "http://localhost:9093/display";
    private final HttpClient httpClient;

    public BarcodePriceSenderService() {
        this.httpClient = HttpClient.newBuilder().build();
    }

    public BarcodePriceSenderService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public SendResult send(String priceMsg) throws IOException, InterruptedException {
        HttpResponse<String> response = httpClient.send(
                HttpRequest.newBuilder()
                        .uri(URI.create(DISPLAY_SERVICE_URL))
                        .POST(HttpRequest.BodyPublishers.ofString("text=" + priceMsg))
                        .build(),
                HttpResponse.BodyHandlers.ofString());

        return new SendResult(response.statusCode(), response.statusCode() == 200 ? priceMsg : null);
    }

    public record SendResult(int statusCode, String priceMsgSent) {}
}
