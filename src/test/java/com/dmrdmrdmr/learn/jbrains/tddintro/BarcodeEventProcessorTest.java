package com.dmrdmrdmr.learn.jbrains.tddintro;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.Authenticator;
import java.net.CookieHandler;
import java.net.ProxySelector;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
        bep.onBarcode(null);
        Assertions.assertEquals("Null barcode", bep.getPostedMessage());
    }

    // t2 - received barcode with no product -> "Product not found" sent
    @ParameterizedTest
    @CsvSource(value = {"012345", "012346", "045678"})
    void testBarcodesWithNoProduct(String barcode) {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of(new Product("112345", null)));
        bep.onBarcode(barcode);
        Assertions.assertEquals("Product not found", bep.getPostedMessage(), barcode);
    }

    // t3 - received barcode with product and product has price -> price sent
    @Test
    void testBarcodeForProductWithPrice() {

        BarcodeEventProcessor bep = new BarcodeEventProcessor(
                List.of(new Product("112345", "25,55$")),
                buildMockHttpClientWithFixedResponseCode(200));

        bep.onBarcode("112345");
        Assertions.assertEquals("25,55$", bep.getPostedMessage());
    }

    // t4 - received barcode with product but no price -> "Price not set" sent
    @Test
    void testBarcodeForProductWithoutPrice() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of(new Product("112345", null)));
        bep.onBarcode("112345");
        Assertions.assertEquals("Price not set", bep.getPostedMessage());
    }

    // t5 - barcode for product with price but HTTP error response -> previous price in sent, and error response stored

    // New things to check ...
    @Test
    void testNullBarcodeWithNullProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(null);
        bep.onBarcode(null);
        Assertions.assertEquals("Null barcode", bep.getPostedMessage());
    }

    @Test
    void testBarcodeWithNullProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(null);
        bep.onBarcode("13213456");
        Assertions.assertEquals("Product not found", bep.getPostedMessage());
    }

    // t7 - empty product list
    @Test
    void testNullBarcodeWithEmptyProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of());
        bep.onBarcode(null);
        Assertions.assertEquals("Null barcode", bep.getPostedMessage());
    }

    @Test
    void testBarcodeWithEmptyProductList() {
        BarcodeEventProcessor bep = new BarcodeEventProcessor(List.of());
        bep.onBarcode("13213456");
        Assertions.assertEquals("Product not found", bep.getPostedMessage());
    }


    private HttpClient buildMockHttpClientWithFixedResponseCode(int responseCode) {
        return new HttpClient() {
            @Override
            public Optional<CookieHandler> cookieHandler() {
                return Optional.empty();
            }

            @Override
            public Optional<Duration> connectTimeout() {
                return Optional.empty();
            }

            @Override
            public Redirect followRedirects() {
                return null;
            }

            @Override
            public Optional<ProxySelector> proxy() {
                return Optional.empty();
            }

            @Override
            public SSLContext sslContext() {
                return null;
            }

            @Override
            public SSLParameters sslParameters() {
                return null;
            }

            @Override
            public Optional<Authenticator> authenticator() {
                return Optional.empty();
            }

            @Override
            public Version version() {
                return null;
            }

            @Override
            public Optional<Executor> executor() {
                return Optional.empty();
            }

            @Override
            public <T> HttpResponse<T> send(HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler)
                    throws IOException, InterruptedException {

                return new HttpResponse<>() {
                    @Override
                    public int statusCode() {
                        return responseCode;
                    }

                    @Override
                    public HttpRequest request() {
                        return null;
                    }

                    @Override
                    public Optional<HttpResponse<T>> previousResponse() {
                        return Optional.empty();
                    }

                    @Override
                    public HttpHeaders headers() {
                        return null;
                    }

                    @Override
                    public T body() {
                        return null;
                    }

                    @Override
                    public Optional<SSLSession> sslSession() {
                        return Optional.empty();
                    }

                    @Override
                    public URI uri() {
                        return null;
                    }

                    @Override
                    public Version version() {
                        return null;
                    }
                };
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                    HttpRequest request, HttpResponse.BodyHandler<T> responseBodyHandler) {

                return null;
            }

            @Override
            public <T> CompletableFuture<HttpResponse<T>> sendAsync(
                    HttpRequest request,
                    HttpResponse.BodyHandler<T> responseBodyHandler,
                    HttpResponse.PushPromiseHandler<T> pushPromiseHandler) {
                return null;
            }
        };
    }
}
