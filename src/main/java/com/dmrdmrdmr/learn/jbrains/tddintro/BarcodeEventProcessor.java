package com.dmrdmrdmr.learn.jbrains.tddintro;

public class BarcodeEventProcessor {

    private final ProductBarcodeService productBarcodeService;
    private final BarcodePriceSenderService barcodePriceSenderService;

    private String lastPriceMessageSent;
    private Integer errorStatusCode;

    public BarcodeEventProcessor(
            ProductBarcodeService productBarcodeService,
            BarcodePriceSenderService barcodePriceSenderService) {

        this.barcodePriceSenderService = barcodePriceSenderService;
        this.productBarcodeService = productBarcodeService;
    }

    public void onBarcode(String barcode) {

        if(productBarcodeService == null) {
            throw new IllegalArgumentException("ProductBarcodeService cannot be null");
        }

        if(barcodePriceSenderService == null) {
            throw new IllegalArgumentException("BarcodePriceSenderService cannot be null");
        }

        try {
            String productPriceMsg = productBarcodeService.getProductPriceMsg(barcode);
            BarcodePriceSenderService.SendResult result = barcodePriceSenderService.send(productPriceMsg);

            if(result.statusCode() == 200) {
                this.lastPriceMessageSent = result.priceMsgSent();
                this.errorStatusCode = null;
            }
        } catch (Exception ignored) {}
    }

    public String getLastPriceMessageSent() {
        return lastPriceMessageSent;
    }

    public Integer getErrorStatusCode() {
        return errorStatusCode;
    }
}
