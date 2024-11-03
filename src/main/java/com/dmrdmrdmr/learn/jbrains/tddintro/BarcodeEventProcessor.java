package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.List;
import java.util.Optional;

public class BarcodeEventProcessor {

    private final List<Product> products;

    private String postedMessage;

    public BarcodeEventProcessor(List<Product> products) {
        this.products = products;
    }


    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = "Null barcode";
        } else if(products != null) {
            this.postedMessage = products.stream()
                    .filter(p -> barcode.equals(p.barcode()))
                    .findFirst()
                    .map(p -> Optional.ofNullable(p.price()).orElse("Price not set"))
                    .orElse("Product not found");
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
