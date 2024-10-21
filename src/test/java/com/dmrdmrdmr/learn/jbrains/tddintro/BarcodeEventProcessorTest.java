package com.dmrdmrdmr.learn.jbrains.tddintro;

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
    // t2 - received barcode with no product -> "Product not found" sent
    // t3 - received barcode with product and product has price -> price sent
    // t4 - received barcode with product but no price -> "Price not set" sent
    // t5 - barcode for product with price but HTTP error response -> previous price in sent, and error response stored


}
