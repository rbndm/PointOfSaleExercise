package com.dmrdmrdmr.learn.jbrains.tddintro;

import com.dmrdmrdmr.learn.jbrains.tddintro.util.MockHttpClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class BarcodePriceSenderServiceTest {

    // test send message and ok response
    @Test
    void testSendAndOkResponse() throws IOException, InterruptedException {

        String expectedMsg = "message";
        int expectedStatusCode = 200;

        BarcodePriceSenderService senderService = new BarcodePriceSenderService(new MockHttpClient(expectedStatusCode));

        BarcodePriceSenderService.SendResult result = senderService.send(expectedMsg);

        Assertions.assertEquals(expectedStatusCode, result.statusCode());
        Assertions.assertEquals(expectedMsg, result.priceMsgSent());
    }

    // TODO test send message and error response
    @Test
    void testSendAndErrorResponse() throws IOException, InterruptedException {

        String expectedMsg = "message";
        int expectedStatusCode = 500;

        BarcodePriceSenderService senderService = new BarcodePriceSenderService(new MockHttpClient(expectedStatusCode));

        BarcodePriceSenderService.SendResult result = senderService.send(expectedMsg);

        Assertions.assertEquals(expectedStatusCode, result.statusCode());
        Assertions.assertNull(result.priceMsgSent());
    }
}