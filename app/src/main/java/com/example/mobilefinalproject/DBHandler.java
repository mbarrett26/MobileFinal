package com.example.mobilefinalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_Name = "notesDb";
    private static final int DB_Version = 1;

    public DBHandler(Context context) {
        super(context, DB_Name, null, DB_Version);
    }

    private static final String DB_Table = "userData";
    private static final String Col_ID = "id";
    private static final String Col_Username = "username";
    private static final String Col_Password = "password";

    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + DB_Table + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_Username + " TEXT, "
                + Col_Password + " TEXT " +
                ")";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table);
        onCreate(db);
    }

    public void registerNewUser(String name, String pass){
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(Col_Username, name);
        values.put(Col_Password, pass);

        // after adding all values we are passing
        // content values to our table.
        db.insert(DB_Table, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }
}
