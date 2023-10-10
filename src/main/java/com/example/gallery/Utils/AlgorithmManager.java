package com.example.gallery.Utils;

import android.os.Build;

public class AlgorithmManager {
    public String Encryption(String text, Algorithms algorithm){
        String code=null;
        switch (algorithm){
            case VISION:
                code=VisionAlgorithm.VisionEncryption(text);
                break;
            case HUFFMAN:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    code=HuffmanAlgorithm.Encryption(text);
                }
                break;
            case LIBRARY:
                code=LibraryAlgorithm.Encryption(text);
                break;
            default:
        }
        return code;
    }

    public String Decryption(String code, Algorithms algorithm){
        String text=null;
        switch (algorithm){
            case VISION:
                text=VisionAlgorithm.VisionDecryption(code);
                break;
            case HUFFMAN:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //text=HuffmanAlgorithm.Decryption(code);
                }
                break;
            case LIBRARY:
                text=LibraryAlgorithm.Decryption(code.getBytes());
                break;
            default:
        }
        return text;
    }
}
