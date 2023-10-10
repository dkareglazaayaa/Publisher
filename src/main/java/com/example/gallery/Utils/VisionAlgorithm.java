package com.example.gallery.Utils;

import java.util.HashMap;

public class VisionAlgorithm {
    private static final String ALPHABET=
            "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ" +
                    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static HashMap<Character,Integer> numAlp=new HashMap<>();
    private final static String KEY="солнце";
    static {
        fillNumAlp();
    }


    public static void fillNumAlp(){
        for(int i=0;i<ALPHABET.length();i++){
            numAlp.put(ALPHABET.charAt(i),i);
        }
    }

    public static String VisionEncryption(String text, String key){
        String encryptionText = "";
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            if (ALPHABET.indexOf(symbol) != -1) {
                int value_1 = numAlp.get(symbol);
                int value_2 = numAlp.get(key.charAt(i % key.length()));

                symbol = ALPHABET.charAt((value_1 + value_2) % ALPHABET.length());

            }
            encryptionText += symbol;

        }
        return encryptionText;
    }

    public static String VisionEncryption(String text){
        return VisionEncryption(text,KEY);
    }

    public static String VisionDecryption(String text,String key){
        String decryptionText = "";
        for (int i = 0; i < text.length(); i++) {
            char symbol = text.charAt(i);

            if (ALPHABET.indexOf(symbol) != -1) {
                int value_1 = numAlp.get(symbol);
                int value_2 = numAlp.get(key.charAt(i % key.length()));

                symbol = ALPHABET.charAt((value_1 - value_2) % ALPHABET.length());

            }
            decryptionText += symbol;

        }
        return decryptionText;
    }

    public static String VisionDecryption(String text) {
        return VisionDecryption(text,KEY);
    }
}
