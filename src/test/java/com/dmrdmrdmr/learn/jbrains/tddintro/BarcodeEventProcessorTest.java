package com.dmrdmrdmr.learn.jbrains.tddintro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        BarcodeEventProcessor bep = new BarcodeEventProcessor();
        bep.onBarcode(null);
        Assertions.assertEquals("Null barcode", bep.getPostedMessage());
    }

    // t2 - received barcode with no product -> "Product not found" sent
    @ParameterizedTest
    @CsvSource(value = {"012345", "012346", "045678"})
    void testBarcodesWithNoProduct(String barcode) {
        BarcodeEventProcessor bep = new BarcodeEventProcessor();
        bep.onBarcode(barcode);
        Assertions.assertEquals("Product not found", bep.getPostedMessage(), barcode);
    }

    // t3 - received barcode with product and product has price -> price sent
    // t4 - received barcode with product but no price -> "Price not set" sent
    // t5 - barcode for product with price but HTTP error response -> previous price in sent, and error response stored


}
