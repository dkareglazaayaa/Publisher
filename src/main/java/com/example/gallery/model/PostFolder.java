package com.example.gallery.model;

import androidx.annotation.Nullable;

public class PostFolder {
    private int id;
    private String imagePath;
    private String soundPath;
    private String postTitle;
    private String postText;
    private String webUrl;
    private final static String DEFAULT_IMAGE="/sdcard/Pictures/no_photo.jpg";

    public PostFolder() {
    }

    public PostFolder(int id,String imagePath, String soundPath, String postTitle, String postText, String webUrl) {
        this.id=id;
        this.imagePath = imagePath;
        this.soundPath = soundPath;
        this.postTitle = postTitle;
        this.postText = postText;
        this.webUrl=webUrl;
    }
    public PostFolder(String imagePath, String soundPath, String postTitle, String postText, String webUrl) {
        this(Publications.getAvalId(),imagePath,soundPath,postTitle,postText,webUrl);
    }
   /* public PostFolder(String soundPath, String postTitle, String postText, String webUrl) {
        this(DEFAULT_IMAGE,soundPath,postTitle,postText,webUrl);
    }*/
    public PostFolder(String soundPath, String postTitle, String postText) {
        this(DEFAULT_IMAGE,soundPath,postTitle,postText,null);
    }
    public PostFolder(String imagePath, String soundPath, String postTitle, String postText) {
        this(imagePath,soundPath,postTitle,postText,null);
    }
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    public String getPostTitle() {
        return postTitle;
    }

    public void setPostTitle(String postTitle) {
        this.postTitle = postTitle;
    }

    public String getPostText() {
        return postText;
    }

    public void setPostText(String postText) {
        this.postText = postText;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this)  return true;

        if (obj == null || obj.getClass() != getClass())
            return false;

        PostFolder post = (PostFolder) obj;
        return post.id==this.id
                && post.getPostTitle().equals(this.getPostTitle());
    }

    @Override
    public int hashCode() {
        return 31*id+15*postTitle.length();
    }
}
