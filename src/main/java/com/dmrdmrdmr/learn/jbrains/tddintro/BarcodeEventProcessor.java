package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.List;

public class BarcodeEventProcessor {

    private final List<String> barcodesWithProduct;
    private String postedMessage;

    public BarcodeEventProcessor() {
        this.barcodesWithProduct = null;
    }

    public BarcodeEventProcessor(List<String> barcodesWithProduct) {
        this.barcodesWithProduct = barcodesWithProduct;
    }


    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = "Null barcode";
        } else if(barcodesWithProduct != null && !barcodesWithProduct.contains(barcode)) {
            this.postedMessage = "Product not found";
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
