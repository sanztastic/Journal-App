package com.example.journalapp.model;

public class Journal {
    private int id;
    private String title;
    private String description;
    private byte[] image;
    private String user_name;
    public Journal(){}

    public Journal(int id, String title, String description, byte[] image, String user_name) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
