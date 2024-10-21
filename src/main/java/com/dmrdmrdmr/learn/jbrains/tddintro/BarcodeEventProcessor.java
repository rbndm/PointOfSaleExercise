package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.List;

public class BarcodeEventProcessor {

    private final List<String> barcodesWithProduct;
    private final List<Product> products = List.of(new Product("112345", null));

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
        } else if(barcodesWithProduct.contains(barcode)) {
            this.postedMessage = products.stream()
                    .filter(p -> p.barcode().equals(barcode))
                    .findAny()
                    .map(Product::price)
                    .orElse("Price not set");
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
