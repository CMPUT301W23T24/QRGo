package com.example.qrgo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QRList {

    private List<QR> qrs = new ArrayList<>();

    public void add(QR qr){
        if (qrs.contains(qr)) {
            throw new IllegalArgumentException();
        }
        qrs.add(qr);
    }

    public List getQRs(){
        List list = qrs;
        Collections.sort(list);
        return list;
    }

    public Boolean hasQR(QR qr){
        if(qrs.contains(qr)){
            return true;
        }
        return false;
    }

    public void delete(QR qr){
        if (!qrs.contains(qr)){
            throw new IllegalArgumentException();
        }
        qrs.remove(qr);
    }

    public Integer countQRs(){return qrs.size();}
}
