package com.rast.uniminiboard;

import java.util.Vector;

public class User {
    private String name;
    private String email;
    private String faculty;
    private String password;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getPassword() {
        return password;
    }


    public User(String id,String name, String email, String faculty) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.faculty = faculty;
    }

    public User(String name, String email, String faculty, String password, String id) {
        this.name = name;
        this.email = email;
        this.faculty = faculty;
        this.password = password;
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
