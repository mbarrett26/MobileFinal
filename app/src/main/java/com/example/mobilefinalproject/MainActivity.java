package com.example.mobilefinalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity{
    //JSON Keys
    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clearCartInSharedPreferences();
    }

    public void clearCartInSharedPreferences() {
        // Get the SharedPreferences instance associated with the CART_PREFS key
        SharedPreferences sharedPreferences = this.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);

        // Create an editor to modify the SharedPreferences data
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Clear the stored value associated with the CART_ITEMS_KEY
        editor.putString(CART_ITEMS_KEY, ""); // Setting an empty string to clear the value

        // Apply the changes made to the SharedPreferences
        editor.apply();
    }

}