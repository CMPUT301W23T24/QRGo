package com.example.qrgo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QRTest {

    private QR mockQR() {
        return new QR("testID", "testUserScanned", 13, "0^0");
    }

    private QRList mockQRList(){
        QRList qrList = new QRList();
        qrList.add(mockQR());
        return qrList;
    }

    @Test
    void testAdd(){
        QRList qrL = mockQRList();
        assertEquals(1, qrL.getQRs().size());
        QR qr = new QR("test", "test2", 2, "test3");
        qrL.add(qr);

//        assertTrue(qrL.getQRs().contains(qr));
//        QR qr2 = new QR("test1", "test2", 3, "test4");
//        qrL.add(qr2);
//        assertEquals(3, qrL.getQRs().size());

    }

    @Test
    void testAddException(){
        QRList qrL = mockQRList();
        QR qr = new QR("a", "b");
        qrL.add(qr);
        assertThrows( IllegalArgumentException.class, () -> {
            qrL.add(qr); });
    }



}
