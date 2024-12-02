package com.dmrdmrdmr.learn.jbrains.tddintro;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        BarcodeEventProcessor barcodeEventProcessor = new BarcodeEventProcessor(
                new ProductBarcodeService(List.of(new Product("100000001", "$1,00"))),
                new BarcodePriceSenderService());

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines().forEach(barcodeEventProcessor::onBarcode);
    }
}