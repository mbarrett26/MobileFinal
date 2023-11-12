package com.example.mobilefinalproject;

import org.json.JSONArray;
import org.json.JSONObject;

public class orderModel {

    private long orderID;

    private long userID;
    private JSONObject orderList;
    private double total;

    public orderModel(){

    }

    public orderModel(JSONObject listInput, double totalInput){
        this.orderList=listInput;
        this.total=totalInput;
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

    public JSONObject getOrderList() {
        return orderList;
    }

    public void setOrderList(JSONObject orderList) {
        this.orderList = orderList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }


}
