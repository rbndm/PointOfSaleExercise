package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.util.List;
import java.util.Optional;

public class ProductBarcodeService {

    public static final String MSG_NULL_BARCODE = "Null barcode";
    public static final String MSG_PRICE_NOT_SET = "Price not set";
    public static final String MSG_PRODUCT_NOT_FOUND = "Product not found";

    private final List<Product> products;

    public ProductBarcodeService(List<Product> products) {
        this.products = products;
    }


    public String getProductPriceMsg(String barcode) {
        String productPriceMsg;
        if(barcode == null) {
            productPriceMsg = MSG_NULL_BARCODE;
        } else {
            if(products != null) {
                productPriceMsg = products.stream()
                        .filter(p -> barcode.equals(p.barcode()))
                        .findFirst()
                        .map(p -> Optional.ofNullable(p.price()).orElse(MSG_PRICE_NOT_SET))
                        .orElse(MSG_PRODUCT_NOT_FOUND);
            } else {
                productPriceMsg = MSG_PRODUCT_NOT_FOUND;
            }
        }
        return productPriceMsg;
    }
}
