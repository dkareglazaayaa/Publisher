package com.example.gallery.Utils;

import com.example.gallery.model.PostFolder;
import com.example.gallery.model.Publications;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class JsonFileManager {
    private final static String PATH = "/sdcard/DCIM/Posts.json";
    private static Gson gson = new Gson();
    private static JsonFileManager jsonFileManager;

    private JsonFileManager() {
    }

    public static JsonFileManager getJsonFileManager() {
        return jsonFileManager;
    }

    private static String GetPrettyJsonString(String jsonString) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(jsonString);
        return gson.toJson(je);
    }

    public static void WriteJsonFile(String path) {
        ArrayList<PostFolder> posts = Publications.getPosts();
        String postsString = gson.toJson(posts);
        try {
            FileWriter file = new FileWriter(PATH);
            file.write(GetPrettyJsonString(postsString));
            file.flush();
        } catch (IOException e) {
        }
    }
    public static void WriteJsonFile() {
        WriteJsonFile(PATH);
    }

    public static void ReadJsonFile(String path) {
        Type listType = new TypeToken<ArrayList<PostFolder>>() {
        }.getType();
        ArrayList<PostFolder> posts = new ArrayList<>();
        try {
            JsonReader reader = new JsonReader(new FileReader(PATH));
            posts = gson.fromJson(reader, listType);
        } catch (FileNotFoundException e) {
        }
        Publications.setPosts(posts);
    }
    public static void ReadJsonFile() {
        ReadJsonFile(PATH);
    }
}
