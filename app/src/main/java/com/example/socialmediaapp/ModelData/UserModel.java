package com.example.socialmediaapp.ModelData;

public class UserModel {

    String ID;
    String user_email;
    String user_pass;

    public UserModel(String ID, String user_email, String user_pass) {
        this.ID = ID;
        this.user_email = user_email;
        this.user_pass = user_pass;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }
}