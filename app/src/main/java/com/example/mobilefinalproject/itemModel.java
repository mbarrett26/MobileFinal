package com.example.mobilefinalproject;

import android.graphics.Bitmap;

import java.sql.Blob;

public class itemModel {

    private long id;
    private String itemName;
    private double price;
    private byte[] image;

    public itemModel(){

    }

    public itemModel(String itemInput, double priceInput, byte[] image){
        this.itemName = itemInput;
        this.price = priceInput;
        this.image = image;
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
}
