package com.example.qrgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creats a list of QRs
 */
public class QRList {

    private List<QR> qrs = new ArrayList<>();

    /**
     * adds a qr to a list if it does not exist
     * @param qr
     */
    public void add(QR qr){
        if (qrs.contains(qr)) {
            throw new IllegalArgumentException();
        }
        qrs.add(qr);
    }

    /**
     * gets all qrs from the list
     * @return list of qrs
     */
    public List getQRs(){
        List list = qrs;

        return list;
    }

    /**
     * checks if a qr is in a class
     * @param qr qr to be checked
     * @return True or False depending the qr is presnet
     */
    public Boolean hasQR(QR qr){
        if(qrs.contains(qr)){
            return true;
        }
        return false;
    }

    /**
     * delets a qr from the list
     * @param qr
     */
    public void delete(QR qr){
        if (!qrs.contains(qr)){
            throw new IllegalArgumentException();
        }
        qrs.remove(qr);
    }

    /**
     * Coutns the qr in the list
     * @return integer amount of qrs
     */
    public Integer countQRs(){return qrs.size();}
}
