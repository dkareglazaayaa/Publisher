package com.example.gallery.Utils;

import android.os.Build;

import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class LibraryAlgorithm {
    private static CipherData cipherData=null;
    static {
        generateCipherData();
    }

    private static void generateCipherData(){
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(256);
            SecretKey keyPair = keyGenerator.generateKey();
            FileManager.WriteFile(keyPair.toString(),"/sdcard/DCIM/DataCode.txt");
            Cipher cipher = Cipher.getInstance("AES");
            cipherData=new CipherData(keyPair, cipher);
        }catch (NoSuchPaddingException | NoSuchAlgorithmException e) {}

    }
    public static String Decryption(byte[] code) {
        String text = null;
        try {
            cipherData.cipher.init(Cipher.DECRYPT_MODE, cipherData.keyPair);
            text =new String(cipherData.cipher.doFinal(code));
        }
        catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {}
        return text;
    }
    public static String Encryption(String text) {
        String code = null;
        try {
            cipherData.cipher.init(Cipher.ENCRYPT_MODE, cipherData.keyPair);
            byte[] encryptedByte = cipherData.cipher.doFinal(text.getBytes());
            Base64.Encoder encoder = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                encoder = Base64.getEncoder();
                code = encoder.encodeToString(encryptedByte);
            }

            //  code = cipherData.cipher.doFinal(text.getBytes());
        } catch (IllegalBlockSizeException | BadPaddingException | InvalidKeyException e) {}

        return code;
    }

    static class CipherData {
        SecretKey keyPair;
        Cipher cipher;

        public CipherData(SecretKey keyPair, Cipher cipher) {
            this.keyPair = keyPair;
            this.cipher = cipher;
        }

        public SecretKey getKeyPair() {
            return keyPair;
        }

        public void setKeyPair(SecretKey keyPair) {
            this.keyPair = keyPair;
        }

        public Cipher getCipher() {
            return cipher;
        }

        public void setCipher(Cipher cipher) {
            this.cipher = cipher;
        }
    }

}
