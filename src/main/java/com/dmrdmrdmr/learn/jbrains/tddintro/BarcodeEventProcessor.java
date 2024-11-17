package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;

public class BarcodeEventProcessor {

    public static final String MSG_NULL_BARCODE = "Null barcode";
    public static final String MSG_PRICE_NOT_SET = "Price not set";
    public static final String MSG_PRODUCT_NOT_FOUND = "Product not found";

    private final List<Product> products;
    private final HttpClient httpClient;

    private String postedMessage;

    private int responseCode;

    public BarcodeEventProcessor(List<Product> products, HttpClient httpClient) {
        this.products = products;
        this.httpClient = httpClient;
    }

    public BarcodeEventProcessor(List<Product> products) {
        this.products = products;
        this.httpClient = HttpClient.newBuilder().build();
    }


    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = MSG_NULL_BARCODE;
        } else {
            if(products != null) {
                    this.postedMessage = products.stream()
                            .filter(p -> barcode.equals(p.barcode()))
                            .findFirst()
                            .map(p -> Optional.ofNullable(p.price()).map(this::sendPrice).orElse(MSG_PRICE_NOT_SET))
                            .orElse(MSG_PRODUCT_NOT_FOUND);
            } else {
                this.postedMessage = MSG_PRODUCT_NOT_FOUND;
            }
        }
    }

    private String sendPrice(String price) {

        String result = this.postedMessage;
        try {
            HttpResponse<String> response = httpClient.send(HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:9393/display"))
                            .POST(HttpRequest.BodyPublishers.ofString("text=" + price))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());

            responseCode = response.statusCode();

            if(responseCode == 200) {
                result = price;
            }

        } catch (IOException ex) {
            responseCode = -100;
        } catch (InterruptedException ex) {
            responseCode = -200;
        }

        return result;
    }

    public String getPostedMessage() {
        return postedMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }
}
