package com.example.qrgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class holds a list of objects of type QR
 */
public class QRList {

    private List<QR> qrs = new ArrayList<>();

    /**
     * Takes in a parameter qr and adds it the the list of type QRs
     * @param qr
     */
    public void add(QR qr){
        if (qrs.contains(qr)) {
            throw new IllegalArgumentException();
        }
        qrs.add(qr);
    }

    /**
     * This method returns the list of type QRs
     * @return
     */
    public List getQRs(){
        List list = qrs;

        return list;
    }

    /**
     * This method checks whether the parameter 'qr' is in the list of type QRs
     * @param qr
     * @return
     * Returns a boolean, true if 'qr' is in the list, false if not
     */
    public Boolean hasQR(QR qr){
        if(qrs.contains(qr)){
            return true;
        }
        return false;
    }

    /**
     * This method deletes the parameter 'qr' from the list of type QRs
     * @param qr
     */
    public void delete(QR qr){
        if (!qrs.contains(qr)){
            throw new IllegalArgumentException();
        }
        qrs.remove(qr);
    }

    /**
     * This method counts the size of the list of type QRs
     * @return
     * Returns an integer representing the size of the list of type QRs
     */
    public Integer countQRs(){return qrs.size();}
}
