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
import com.google.firebase.firestore.GeoPoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads what the user scanned and provides the necessary details
 */
public class QRReader {
    private Dictionary<String, Integer> dict = new Hashtable<>();
    private String[][] nameChoices = {
            {"the ", "a "},
            {"greatly ", "mundanely "},
            {"unique ", "average "},
            {"joy", "strange"},
            {"pig ", "sir "},
            {"bildalf","gando"}
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

    /**
     * This method is responsible for the scanning of QR's
     * @return
     * Returns a boolean, true if the QR already exits, false if not
     */
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
    }

    /**

    //https://www.geeksforgeeks.org/sha-256-hash-in-java/

    /**
     * This method creates a hash for the QRs scanned
     * @param content
     * @return
     * Returns the content of the hash for the QR
     */
    public String createHash(String content){
        if (content.length() > 0) {
            MessageDigest messageDigest;
            try {
                messageDigest = MessageDigest.getInstance("SHA-256");
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
            bytes = messageDigest.digest(content.getBytes());

            StringBuilder sb = new StringBuilder(); // Creating the hash for the QR
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02x", bytes[i]));
            }
            hashedContent = sb.toString();
            return hashedContent;
        }
        return "";
    }


    //https://www.geeksforgeeks.org/matcher-group-method-in-java-with-examples/?ref=rp
    /**
     * This method calculates the score for each QR code
     * @param hash
     * @return
     * Returns the score of the QR code by using it's hashed value
     */
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

    /**
     * creates a dictionary of Key value based on hexadecimals
     *

     */
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


    /**
     * This method creates the face of the QR based off the hash
     * @param hash
     * @return
     * Returns the face of the QR
     */
//    public String createFace(String hash){
////        face = "_______";
////        Character bit;
////        String evenChars = "02468ace";
////        for (int i = 0; i < 6; i++){
////            bit = hash.charAt(i);
////            if (evenChars.contains(bit.toString()) ){
////                face += imageChoice[i][0];
////            } else {
////                face += imageChoice[i][1];
////            }
////        }
////        face += "-----------";
////        return face;
//    }


//    public String createFace(String hash) throws IOException {
//        String imageUrl = String.format("https://robohash.org/%s.JPG?set=set3", hash);
//        RobohashApiClient apiClient = new RobohashApiClient();
//        apiClient.requestImageAsync(imageUrl, new RobohashApiClient.OnCompleteListener() {
//            public void onComplete(String imageAsString, Exception e) {
//                if (e != null) {
//                    e.printStackTrace();
//                    // Handle the failure case
//                } else {
//                    System.out.println("Received image as string in QRReader: " + imageAsString);
////                    return imageAsString;
//                    // Access the imageAsString and perform further processing or update UI, etc.
//                }
//            }
//        });
//
//    }

    public CompletableFuture<String> createFace(String hash) throws IOException {
        String imageUrl = String.format("https://robohash.org/%s.PNG?set=set3", hash);
        RobohashApiClient apiClient = new RobohashApiClient();
        return apiClient.requestImageAsync(imageUrl);
    }

    public static void main(String[] args) {
        QRReader qrReader = new QRReader();
        try {
            CompletableFuture<String> resultFuture = qrReader.createFace("your-hash");
            resultFuture.thenAccept(imageAsString -> {
                System.out.println("Received image as string: " + imageAsString);
                // Handle the result as needed
            }).exceptionally(e -> {
                e.printStackTrace();
                // Handle the exception if it occurs
                return null;
            });
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception if it occurs
        }
    }


    /**
     * This method creates the name for the QR
     * @param hash
     * @return
     * Returns the given name of the QR
     */
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

    /**
     * This method adds the hash and QR to the database
     * @param hash
     * @param qr
     */
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
                        String test = "test";

                        ref.update("scannedAmnt", FieldValue.increment(1));
                        ref.update("scannedBy", FieldValue.arrayUnion(qr.getScannedBy()));
                        ref.update("comments", FieldValue.arrayUnion());

                    } else {
                        Log.d(TAG, "DNE");
                        db.collection("qr").document(hash).set(qr);
                        db.collection("qr").document(hash).update("scannedBy", FieldValue.arrayUnion(qr.getScannedBy()));
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });

    }

    /**
     * remove the QR from db
     * @param hash
     * @param qr
     */
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
                        ref.update("location", FieldValue.arrayRemove(qr.getScannedBy()));

//                        ref.update("comments", FieldValue.arrayRemove()); // for now do not delete comments

                    } else {
                        Log.d(TAG, "DNE");
                        throw new IllegalArgumentException();
                    }
                } else {
                    Log.d(TAG, "Failed with: ", task.getException());
                }
            }
        });
    }

    /**
     * This method adds the location to the database
     * @param hash
     * @param user
     * @param latitude
     * @param longitude
     */
    public void addLocationToDB (String hash, String user, double latitude, double longitude) {
        db = FirebaseFirestore.getInstance();
        DocumentReference ref = db.collection("qr").document(hash);
        GeoPoint point = new GeoPoint(latitude, longitude);
        List<Double> location = Arrays.asList(latitude, longitude);
        Map<String, List<Double>> locations = new HashMap<>();
        locations.put(user, location);
        Log.d("Locations", locations.toString());
        ref.update("locations", FieldValue.arrayUnion(point));
    }
}


