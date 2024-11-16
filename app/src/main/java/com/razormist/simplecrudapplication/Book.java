package com.razormist.simplecrudapplication;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String title;
    private String author;
    private String description;
    private Boolean isBookRead;
    private byte[] imageBook;
    private double price; // Новое поле для цены

    // Конструкторы, геттеры и сеттеры

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean getIsBookRead() {
        return isBookRead;
    }

    public void setIsBookRead(boolean newIsBookRead) {
        this.isBookRead = newIsBookRead;
    }

    public byte[] getImage() {
        return imageBook;
    }

    public void setImage(byte[] newImage) {
        this.imageBook = newImage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

}
