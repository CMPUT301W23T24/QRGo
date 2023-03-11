package com.example.qrgo;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads what the user scanned and provides the necessary details
 */
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
            {"\n| ~   ~ |",   "\n| ==  == |" },
            {"\n|0    0|",  "\n|^    ^|"},
            {"\n|(--)|", "\n|   ||   |\n|   LL   |"},
            {"\n|====|",  "\n|          |"},
            {"\n| ,      , |\n|   ```   |", "\n|  ___  |\n| '     ' |"},
            {"\n|   <<   |\n", "\n|    ||    |\n"}
    };
    private byte[] bytes;
    FusedLocationProviderClient fusedLocationProviderClient;
    public String hashedContent;
    public Integer score = 0;
    public String name = "";
    public String face = "";
    FirebaseFirestore db;
    // TODO get the collection and verify if that which is scanned exists or not

    public boolean readQR(){
        final boolean qrExists = false;
        db = FirebaseFirestore.getInstance();

        DocumentReference ref = db.collection("qr").document(hashedContent);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        Log.d(TAG, "exists!");

                    } else {
                        Log.d(TAG, "no!");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

        return qrExists;
        //check if it exists
        //if (qrExists){} else {}
        //TODO if it does exist, get the right QR and update the num counted
        //TODO add it to the user DB and the QRDB



    }
    //https://www.geeksforgeeks.org/sha-256-hash-in-java/
    public String createHash(String content){
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
        return hashedContent;
    }
    //https://www.geeksforgeeks.org/matcher-group-method-in-java-with-examples/?ref=rp
    public Integer calcScore(String hash){
        String repeatedChars;
        Double scoreD;
        Character character;
        Pattern p = Pattern.compile("(\\w)\\1+");
        Matcher m = p.matcher(hash);
        fillDictionary();
        while (m.find()) {
            repeatedChars = m.group();
            character = repeatedChars.charAt(0);
            scoreD = Math.pow( Double.valueOf(dict.get(character.toString())), Double.valueOf(repeatedChars.length() - 1));
            score += scoreD.intValue();
        }
        return score;

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


    public String createFace(String hash){
        face = "_______";
        Character bit;
        String evenChars = "02468ace";
        for (int i = 0; i < 6; i++){
            bit = hash.charAt(i);
            if (evenChars.contains(bit.toString()) ){
                face += imageChoice[i][0];
            } else {
                face += imageChoice[i][1];
            }
        }
        face += "-----------";
        return face;
    }

    public String createName(String hash){
        Character bit;
        String evenChars = "02468ace";
        for (int i = 0; i < 6; i++){
            bit = hash.charAt(i);
            if (evenChars.contains(bit.toString()) ){
                name += nameChoices[i][0];
            } else {
                name += nameChoices[i][1];
            }
        }
        return name;
    }

    public void addToDB(String hash, QR qr){
        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("qr").document(hash);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        Log.d(TAG, "exists!");
                        ref.update("scannedAmnt", FieldValue.increment(1));
                        ref.update("scannedBy", FieldValue.arrayUnion(qr.getScannedBy()));
                        ref.update("comments", FieldValue.arrayUnion());

                    } else {
                        Log.d(TAG, "DNE");
                        db.collection("qr").document(hash).set(qr);

                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }
    public void removeFromDB(String hash, QR qr){
        //
        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("qr").document(hash);
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if (doc.exists()){
                        Log.d(TAG, "exists!");
                        ref.update("scannedAmnt", FieldValue.increment(-1));
                        ref.update("scannedBy", FieldValue.arrayRemove(qr.getScannedBy()));
                        ref.update("comments", FieldValue.arrayRemove());

                    } else {
                        Log.d(TAG, "DNE");
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }
}


