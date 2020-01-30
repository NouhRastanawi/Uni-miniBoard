package com.rast.uniminiboard;

import java.util.Vector;

public class Post {
    private String date;
    private String title;
    private String content;
    private String userName;
    private String email;
    private String id;

    public Post() {

    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return userName;
    }


    public Post(String id, String title, String content, String userName, String email) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.email = email;
    }

    public Post(String id, String title, String content, String userName, String email, String date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
        this.email = email;
        this.date = date;
    }


    public void setUserName(String userNmae) {
        this.userName = userNmae;
    }
}
