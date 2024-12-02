package com.dmrdmrdmr.learn.jbrains.tddintro;

public class BarcodeEventProcessor {

    private final ProductBarcodeService productBarcodeService;
    private final BarcodePriceSenderService barcodePriceSenderService;

    public BarcodeEventProcessor(ProductBarcodeService productBarcodeService, BarcodePriceSenderService barcodePriceSenderService) {
        this.barcodePriceSenderService = barcodePriceSenderService;
        this.productBarcodeService = productBarcodeService;
    }

    public void onBarcode(String number) {

        if(productBarcodeService == null) {
            throw new IllegalArgumentException("ProductBarcodeService cannot be null");
        }

        if(barcodePriceSenderService == null) {
            throw new IllegalArgumentException("BarcodePriceSenderService cannot be null");
        }

    }
}
