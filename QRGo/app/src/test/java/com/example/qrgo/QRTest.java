package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class QRTest {

    @Test
    void GetSetQRSearch() {
        QR mockQR = new QR("", "", "");
        // Test the id getter and setter
        assertEquals("", mockQR.getId());
        assertEquals("", mockQR.getFace());

        // Change the attributes
        mockQR.setFace("1");
        mockQR.setId("1");
        assertEquals("1", mockQR.getFace());
        assertEquals("1", mockQR.getId());

    }
    @Test
    void GetQR() {
        // String id, String scannedBy, Integer score, String face
        QR testQR = new QR("111", "111", 0, "111");
        assertEquals("111", testQR.getId());
        assertEquals("111", testQR.getScannedBy());
        assertEquals(0, (int) testQR.getScore());
        assertEquals("111", testQR.getFace());

        // Change the attributes
        testQR.setFace("222");
        testQR.setId("222");
        testQR.setScannedBy("abc");
        testQR.setScore(10);
        assertEquals("222", testQR.getFace());
        assertEquals("222", testQR.getId());
        assertEquals("abc", testQR.getScannedBy());
        assertEquals(10, (int) testQR.getScore());

    }
}
