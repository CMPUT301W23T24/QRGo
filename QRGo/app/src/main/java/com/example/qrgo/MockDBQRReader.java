package com.example.qrgo;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MockDBQRReader {
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
            System.out.print(hashedContent);
            return hashedContent;
        }
        return "";
    }
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
}
