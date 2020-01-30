package com.rast.uniminiboard;

public class Comment {
    private String date;
    private String content;
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Comment() {
    }

    public Comment(String id, String name, String content, String date) {
        this.date = date;
        this.content = content;
        this.id = id;
        this.name = name;
    }
}
