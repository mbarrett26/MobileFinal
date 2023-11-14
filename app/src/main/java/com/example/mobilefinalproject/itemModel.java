package com.example.mobilefinalproject;

public class itemModel {

    private long id;
    private String itemName;
    private double price;

    public itemModel(){

    }

    public itemModel(String itemInput, double priceInput){
        this.itemName = itemInput;
        this.price = priceInput;
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
}
