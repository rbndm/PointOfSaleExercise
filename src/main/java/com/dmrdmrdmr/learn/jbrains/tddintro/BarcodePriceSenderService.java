package com.dmrdmrdmr.learn.jbrains.tddintro;

public class BarcodePriceSenderService {

    public SendResult send(String priceMsg) {
        return null;
    }

    public record SendResult(int statusCode, String priceMsgSent) {}
}
