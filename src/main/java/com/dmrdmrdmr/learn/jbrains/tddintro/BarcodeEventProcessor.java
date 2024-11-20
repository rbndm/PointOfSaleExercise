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



    public BarcodeEventProcessor(List<Product> products) {
        this(products, HttpClient.newBuilder().build());
    }

    public BarcodeEventProcessor(List<Product> products, HttpClient httpClient) {
        this.products = products;
        this.httpClient = httpClient;
    }


    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = MSG_NULL_BARCODE;
        } else {
            if(products != null) {
                this.postedMessage = products.stream()
                        .filter(p -> barcode.equals(p.barcode()))
                        .findFirst()
                        .map(p -> Optional.ofNullable(p.price())
                                .map(this::sendPriceToServer)
                                .orElse(MSG_PRICE_NOT_SET))
                        .orElse(MSG_PRODUCT_NOT_FOUND);
            } else {
                this.postedMessage = MSG_PRODUCT_NOT_FOUND;
            }
        }
    }

    private String sendPriceToServer(String price) {

        try {
            HttpResponse<String> response = httpClient.send(
                    HttpRequest.newBuilder()
                            .POST(HttpRequest.BodyPublishers.ofString("text=" + price))
                            .uri(URI.create("http://localhost:9393/display"))
                            .build(),
                    HttpResponse.BodyHandlers.ofString());

            if(response.statusCode() == 200) {
                return price;
            } else {
                return postedMessage;
            }

        } catch (IOException | InterruptedException e) {
            return postedMessage;
        }

    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
