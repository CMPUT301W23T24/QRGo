package com.example.qrgo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Dictionary;

public class QRReaderTest{
    public MockDBQRReader MockReader() {
        MockDBQRReader qrReader = new MockDBQRReader();
        return qrReader;
    }
    @Test
    void CreateHash() {
        MockDBQRReader qrReader = MockReader();
        String content = "TestQR";

        content = qrReader.createHash(content);

        assertEquals(content, "9dbb7fffd2fa9f2cfe1845d24fcae466dd8a6030a2c7dcb2e46bbdc1e8d51e67");
        
    }
    @Test
    void CalcScore() {
        MockDBQRReader qrReader = MockReader();
        String hash = "9dbb7fffd2fa9f2cfe1845d24fcae466dd8a6030a2c7dcb2e46bbdc1e8d51e67";
        int score = qrReader.calcScore(hash);
        assertEquals(score, 266);
    }

    @Test
    void CreateFace() {
        MockDBQRReader qrReader = MockReader();
        String face = qrReader.createFace("9dbb7fffd2fa9f2cfe1845d24fcae466dd8a6030a2c7dcb2e46bbdc1e8d51e67");
        assertEquals(face, "_______\n" +
                "| ==  == |\n" +
                "|^    ^|\n" +
                "|   ||   |\n" +
                "|   LL   |\n" +
                "|          |\n" +
                "|  ___  |\n" +
                "| '     ' |\n" +
                "|    ||    |\n" +
                "-----------");
    }
    @Test
    void CreateName() {
        MockDBQRReader qrReader = MockReader();
        String name = qrReader.createName("9dbb7fffd2fa9f2cfe1845d24fcae466dd8a6030a2c7dcb2e46bbdc1e8d51e67");
        assertEquals(name, "A Mundanely Average StrangeSir Gando");

    }
}
