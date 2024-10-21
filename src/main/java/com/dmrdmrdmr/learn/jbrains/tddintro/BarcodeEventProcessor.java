package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.Set;

public class BarcodeEventProcessor {

    private final Set<String> barcodesWithoutProduct;
    private String postedMessage;

    public BarcodeEventProcessor() {
        this(null);
    }

    public BarcodeEventProcessor(Set<String> barcodesWithoutProduct) {
        this.barcodesWithoutProduct = barcodesWithoutProduct;
    }

    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = "Null barcode";
        } else if(barcodesWithoutProduct.contains(barcode)) {
            this.postedMessage = "Product not found";
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
