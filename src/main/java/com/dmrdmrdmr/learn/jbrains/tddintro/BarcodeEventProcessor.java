package com.dmrdmrdmr.learn.jbrains.tddintro;

public class BarcodeEventProcessor {

    private String postedMessage;

    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = "Null barcode";
        } else if(barcode.equals("012345")) {
            this.postedMessage = "Product not found";
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
