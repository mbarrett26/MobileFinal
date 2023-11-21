package com.example.mobilefinalproject;

import android.graphics.Bitmap;

import java.sql.Blob;

public class itemModel {

    private long id;
    private String itemName;
    private double price;
    private byte[] image;
    private String description;
    private int calories;
    private String category;

    public itemModel(){

    }

    public itemModel(String itemInput, double priceInput, byte[] image, String description, int calories, String category){
        this.itemName = itemInput;
        this.price = priceInput;
        this.image = image;
        this.description = description;
        this.calories = calories;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
