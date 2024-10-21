package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.Set;

public class BarcodeEventProcessor {

    private String postedMessage;

    public void onBarcode(String barcode) {
        if(barcode == null) {
            this.postedMessage = "Null barcode";
        } else if(Set.of("012345", "012346", "045678").contains(barcode)) {
            this.postedMessage = "Product not found";
        }
    }

    public String getPostedMessage() {
        return postedMessage;
    }
}
