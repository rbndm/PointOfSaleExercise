package com.dmrdmrdmr.learn.jbrains.tddintro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

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


    // barcode event processor breaks if it does not have a non null ProductBarcodeService and PriceMessageSenderService
    @ParameterizedTest
    @MethodSource("testAnyNullDependencyFails_source")
    void testAnyNullDependencyFails(ProductBarcodeService productBarcodeService, BarcodePriceSenderService barcodePriceSenderService) {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(productBarcodeService, barcodePriceSenderService);
        Assertions.assertThrows(Exception.class, ()-> bep.onBarcode("1231231"));
    }

    public static Stream<Arguments> testAnyNullDependencyFails_source() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, new BarcodePriceSenderService()),
                Arguments.of(new ProductBarcodeService(List.of()), null)
        );
    }


    // onBarcode, if ProductBarcodeService breaks, does nothing
    @Test
    void testOnBarcodeAndProductBarcodeServiceFails() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(new ProductBarcodeService(List.of()) {

            @Override
            public String getProductPriceMsg(String barcode) {
                throw new NullPointerException("Bonk!");
            }
        }, new BarcodePriceSenderService());

        bep.onBarcode("234235234");

        Assertions.assertNull(bep.getLastPriceMessageSent());
    }

    // TODO onBarcode, if PriceMessageSenderService breaks, stores the error status


    // TODO onBarcode, if everything is ok, stores the price sent correctly
    @Test
    void testOnBarcodeHappyPath() {
        String expectedPriceMessage = "25,13$";
        BarcodeEventProcessor bep = new BarcodeEventProcessor(
                new ProductBarcodeService(List.of()) {
                    @Override
                    public String getProductPriceMsg(String barcode) {
                        return expectedPriceMessage;
                    }
                },
                new BarcodePriceSenderService() {
                    @Override
                    public SendResult send(String priceMsg) {
                        return new SendResult(200, expectedPriceMessage);
                    }
                });

        bep.onBarcode("00010101");

        Assertions.assertEquals(expectedPriceMessage, bep.getLastPriceMessageSent());
        Assertions.assertEquals(null, bep.getErrorStatusCode());
    }

}
