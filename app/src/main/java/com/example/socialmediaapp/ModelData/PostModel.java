package com.example.socialmediaapp.ModelData;

import com.example.socialmediaapp.R;

import java.util.Date;

public class PostModel {

    long postCounter;
    String username;
    String Imagename;
    String ImageURL;
    String postMessage;
    int like;
//    int likebtn;
    String postTime;

    public PostModel(){
    }

    public PostModel(long postCounter, String username, String imagename, String imageURL, String postMessage, int like, String postTime) {

        this.postCounter = postCounter;
        this.username = username;
        Imagename = imagename;
        ImageURL = imageURL;
        this.postMessage = postMessage;
        this.like = like;
        this.postTime = postTime;
//        this.likebtn = likebtn;
    }

    public long getPostCounter() {
        return postCounter;
    }

    public void setPostCounter(long postCounter) {
        this.postCounter = postCounter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImagename() {
        return Imagename;
    }

    public void setImagename(String imagename) {
        Imagename = imagename;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getPostMessage() {
        return postMessage;
    }

    public void setPostMessage(String postMessage) {
        this.postMessage = postMessage;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

//    public int getLikebtn(int redheart) {
//        return likebtn;
//    }
//
//    public void setLikebtn(int likebtn) {
//        this.likebtn = likebtn;
//    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }
}