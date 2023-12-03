package com.example.mobilefinalproject;

import org.json.JSONArray;
import org.json.JSONObject;

public class orderModel {

    private long orderID;

    private long userID;
    private String orderList;

    @Override
    public String toString() {
        return "userID=" + userID +
                ", orderList='" + orderList + '\'' +
                ", total=" + total +
                '}';
    }

    private double total;

    public orderModel(){

    }

    public orderModel(String listInput, double totalInput, long idInput){
        this.orderList=listInput;
        this.total=totalInput;
        this.userID = idInput;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public long getOrderID() {
        return orderID;
    }

    public void setOrderID(long orderID) {
        this.orderID = orderID;
    }

    public String getOrderList() {
        return orderList;
    }

    public void setOrderList(String orderList) {
        this.orderList = orderList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
