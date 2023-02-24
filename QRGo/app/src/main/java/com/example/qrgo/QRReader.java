package com.example.qrgo;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QRReader {
    private Dictionary<String, Integer> dict = new Hashtable<>();
    private String[][] nameChoices = {
            {"The ", "A "},
            {"Greatly ", "Mundanely "},
            {"Unique ", "Average "},
            {"Joy", "Strange"},
            {"Pig ", "Sir "},
            {"Bildalf","Gando"}
    };
    private String[][] imageChoice = {
            {"\n| ~    ~ |", "\n| ==  == |" },
            {"\n| 0    0 |", "\n| ^    ^ |"},
            {"\n|  (--)  |", "\n|   ||   |\n|   LL   |"},
            {"\n|  ====  |", "\n|        |"},
            {"\n| '    ' |\n|  ```   |", "\n|   ---  |\n| '    ' |"},
            {"\n|   <<   |\n", "\n|        |\n"}
    };
    private String content;
    private byte[] bytes;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String hashedContent;
    public Integer score;
    public String name = "";
    public String face = "";
    FirebaseFirestore db;
    // TODO get the collection and verify if that which is scanned exists or not

    public void readQR(){
        boolean qrExists = false;
        db = FirebaseFirestore.getInstance();
        createHash();
        //check if it exists
        CollectionReference collectionReference = db.collection("user");
        if (qrExists){
            //TODO if it does exist, get the right QR and update the num counted
        } else {
            //TODO add it to the user DB and the QRDB
            calcScore();
            createFaceAndName();
        }

    }
    private void createHash(){
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        bytes = messageDigest.digest(content.getBytes());

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(String.format("%02x", bytes[i]));
        }
        hashedContent = sb.toString();
    }

    private void calcScore(){
        String repeatedChars;
        Double scoreD;
        Character character;
        Pattern p = Pattern.compile("(\\w)\\1+");
        Matcher m = p.matcher(hashedContent);
        fillDictionary();

        while (m.find()) {
            repeatedChars = m.group();
            character = repeatedChars.charAt(0);

            scoreD = Math.pow( Double.valueOf(dict.get(character.toString())), Double.valueOf(repeatedChars.length() - 1));
            score += scoreD.intValue();
        }


    }

    private void fillDictionary(){
        dict.put("0", 20);
        Integer i;
        for (i = 1; i < 10; i++){
            dict.put(i.toString(), i);
        }

        for (Character c = 'a'; c <= 'f'; c++){
            dict.put(c.toString(), i);
            i++;
        }
    }


    private void createFaceAndName(){
        face = " --------";
        Character bit;
        String evenChars = "02468ace";
        for (int i = 0; i < 6; i++){
            bit = hashedContent.charAt(i);
            if (evenChars.contains(bit.toString()) ){
                face += imageChoice[i][0];
                name += nameChoices[i][0];
            } else {
                face += imageChoice[i][1];
                name += nameChoices[i][1];
            }
            face += " --------";
        }

    }
}


