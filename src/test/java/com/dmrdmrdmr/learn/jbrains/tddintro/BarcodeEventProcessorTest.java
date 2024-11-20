package com.dmrdmrdmr.learn.jbrains.tddintro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

public class BarcodeEventProcessorTest {

    /*
                      _____              _____________
                     ¦     ¦   request  ¦             ¦
        barcode ---> ¦ POS ¦ ---------> ¦ HTTP server ¦
                     ¦_____¦ <--------- ¦_____________¦
                               response

        1) receive barcode
        2) get product for barcode
        3) get product price
        4) send price
     */

    // t1 - received null barcode -> "Null barcode" sent
    @Test
    void testNullBarcode() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(null);
        String productPrice = bep.getProductPrice(null);
        Assertions.assertEquals("Null barcode", productPrice);
    }

    // t2 - received barcode with no product -> "Product not found" sent
    @ParameterizedTest
    @CsvSource(value = {"012345", "012346", "045678"})
    void testBarcodesWithNoProduct(String barcode) {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of(new Product("112345", null)));
        String productPrice = bep.getProductPrice(barcode);
        Assertions.assertEquals("Product not found", productPrice, barcode);
    }

    // t3 - received barcode with product and product has price -> price sent
    @Test
    void testBarcodeWithProductAndPrice() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of(new Product("3523452", "22.54$")));
        String productPrice = bep.getProductPrice("3523452");
        Assertions.assertEquals("22.54$", productPrice);
    }

    // t4 - received barcode with product but no price -> "Price not set" sent
    @Test
    void testBarcodeForProductWithoutPrice() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of(new Product("112345", null)));
        String productPrice = bep.getProductPrice("112345");
        Assertions.assertEquals("Price not set", productPrice);
    }

    // t5 - barcode for product with price but HTTP error response -> previous price in sent, and error response stored

    // New things to check ...
    @Test
    void testNullBarcodeWithNullProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(null);
        bep.getProductPrice(null);
        Assertions.assertEquals("Null barcode", bep.getPostedMessage());
    }

    @Test
    void testBarcodeWithNullProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(null);
        bep.getProductPrice("13213456");
        Assertions.assertEquals("Product not found", bep.getPostedMessage());
    }

    // t7 - empty product list
    @Test
    void testNullBarcodeWithEmptyProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of());
        bep.getProductPrice(null);
        Assertions.assertEquals("Null barcode", bep.getPostedMessage());
    }

    @Test
    void testBarcodeWithEmptyProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of());
        bep.getProductPrice("13213456");
        Assertions.assertEquals("Product not found", bep.getPostedMessage());
    }
}
