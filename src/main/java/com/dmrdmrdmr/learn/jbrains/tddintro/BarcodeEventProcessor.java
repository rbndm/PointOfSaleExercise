package com.dmrdmrdmr.learn.jbrains.tddintro;

public class BarcodeEventProcessor {

    private ProductBarcodeService productBarcodeService;
    private BarcodePriceSenderService barcodePriceSenderService;

    public void onBarcode(String number) {

        if(productBarcodeService == null) {
            throw new IllegalArgumentException("ProductBarcodeService cannot be null");
        }

        if(barcodePriceSenderService == null) {
            throw new IllegalArgumentException("BarcodePriceSenderService cannot be null");
        }

    }
}
