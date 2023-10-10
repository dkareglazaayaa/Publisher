package com.example.gallery.Utils;

import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileManager {
    private final static String PATH = "/sdcard/DCIM/Posts.json";

    private final static String HUFFMAN_PATH = "/sdcard/DCIM/Huffman.txt";
    private final static String VISION_PATH = "/sdcard/DCIM/Vision.txt";
    private final static String LIBRARY_PATH = "/sdcard/DCIM/Library.txt";
    private static Gson gson = new Gson();
    private static FileManager fileManager;

    private FileManager() {
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    private static String GetPrettyJsonString(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(jsonString);
        return gson.toJson(je);
    }

    public static String getFileText(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try{
        BufferedReader reader = new BufferedReader( new FileReader (PATH));
        String line = null;
        //String ls = System.getProperty("line.separator");
        while( ( line = reader.readLine() ) != null ) {
            stringBuilder.append( line );
           //stringBuilder.append( ls );
        }
        stringBuilder.deleteCharAt(stringBuilder.length()-1);}
        catch (IOException e){}

        return stringBuilder.toString();
    }

    /*public static void WriteFile(String text) {
        WriteFile(text,"");
    }*/

    private static void writeToHuffman(String text){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String code=HuffmanAlgorithm.Encryption(text);
            WriteFile(code,HUFFMAN_PATH);
        }

    }
    private static void writeToVision(String text){
        String code=VisionAlgorithm.VisionEncryption(text);
        WriteFile(code,VISION_PATH);
    }
    private static void writeToLibrary(String text){
        String code=LibraryAlgorithm.Encryption(text);
        WriteFile(code,LIBRARY_PATH);
    }
    public static void WriteFile() {
        String text=getFileText(PATH);
        String text1=text;
        String text2=text;
        writeToVision(text);
        writeToHuffman(text1);
        writeToLibrary(text2);
    }
    public static void WriteFile(String text,String path) {
        try {
            FileWriter file = new FileWriter(path);
            file.write(text);
            file.flush();
        } catch (IOException e) {
        }
    }
}
