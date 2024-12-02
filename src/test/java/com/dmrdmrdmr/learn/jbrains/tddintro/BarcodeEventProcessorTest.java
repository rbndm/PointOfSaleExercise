package com.dmrdmrdmr.learn.jbrains.tddintro;

public class BarcodeEventProcessorTest {

    /*
                      _____              _____________
                     ¦     ¦   request  ¦             ¦
        barcode ---> ¦ POS ¦ ---------> ¦ HTTP server ¦
                     ¦_____¦ <--------- ¦_____________¦
                               response

        1) receive barcode
        2) get product price message
        3) send it
     */

    // TODO barcode event processor breaks if it does not have a non null ProductBarcodeService and PriceMessageSenderService
    // TODO onBarcode, if ProductBarcodeService breaks, does nothing
    // TODO onBarcode, if PriceMessageSenderService breaks, stores the error status
    // TODO onBarcode, if everything is ok, stores the price sent correctly

}
