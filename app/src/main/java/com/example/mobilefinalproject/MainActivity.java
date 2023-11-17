package com.example.mobilefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHandler db = new DBHandler(this);

        byte[] burger = bitmapToByte(getResources().getDrawable(R.drawable.burger));
        /*
        db.addItem(new itemModel("Burger", 5.99, burger));
        db.addItem(new itemModel("Fries", 2.99, burger));
        db.addItem(new itemModel("Pizza", 15.99, burger));
        db.addItem(new itemModel("Chicken", 25.99, burger));
        */
    }

    public byte[] bitmapToByte(Drawable image){
        Bitmap bitmap = ((BitmapDrawable)image).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitmapdata = stream.toByteArray();

        return bitmapdata;
    }

}