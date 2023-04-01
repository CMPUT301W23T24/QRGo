package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QRListTest {

    private QR mockQR() {
        return new QR("testID", "testUserScanned", 13, "0^0");
    }

    private String mockHash() {return new QRReader().createHash("ABC") ;}
    private QRList mockQRList(){
        QRList qrList = new QRList();
        qrList.add(mockQR());
        return qrList;
    }

    @Test
    void testAddQR(){
        QRList qrL = mockQRList();
        assertEquals(1, qrL.getQRs().size());
        QR qr = new QR("a", "b", 1, "3");
        qrL.add(qr);
        assertEquals(2, qrL.getQRs().size());
    }

    @Test
    void testAddException(){
        QRList qrL = mockQRList();
        QR qr = new QR("a", "b", "c",1);
        qrL.add(qr);
        assertThrows( IllegalArgumentException.class, () -> {
            qrL.add(qr); });
    }

    @Test
    void testCreateHash(){

    }
    @Test
    void testAssertCreateHash(){
        String contentTest = "";
        QRReader qrR = new QRReader();
        String test = qrR.createHash(contentTest);
        assertEquals("",test);

    }

    @Test
    void testAddToDB(){
        //technically cannot due to blocking so I made a QRList that should check the same things

    }



}
