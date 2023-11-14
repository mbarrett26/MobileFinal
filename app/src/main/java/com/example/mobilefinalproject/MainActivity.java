package com.example.mobilefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        DBHandler db = new DBHandler(this);

        db.addItem(new itemModel("Burger", 5.99));
        db.addItem(new itemModel("Fries", 2.99));
        db.addItem(new itemModel("Pizza", 15.99));
        db.addItem(new itemModel("Chicken", 25.99));
        */
    }

}