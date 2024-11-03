package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.List;
import java.util.Optional;

public class BarcodeEventProcessor {

    public static final String MSG_NULL_BARCODE = "Null barcode";
    public static final String MSG_PRICE_NOT_SET = "Price not set";
    public static final String MSG_PRODUCT_NOT_FOUND = "Product not found";

    private final List<Product> products;

    private String postedMessage;

    public BarcodeEventProcessor(List<Product> products) {
        this.products = products;
    }


    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = MSG_NULL_BARCODE;
        } else {
            if(products != null) {
                this.postedMessage = products.stream()
                        .filter(p -> barcode.equals(p.barcode()))
                        .findFirst()
                        .map(p -> Optional.ofNullable(p.price()).orElse(MSG_PRICE_NOT_SET))
                        .orElse(MSG_PRODUCT_NOT_FOUND);
            } else {
                this.postedMessage = MSG_PRODUCT_NOT_FOUND;
            }
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
