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
        String query = "CREATE TABLE " + DB_Table + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_Username + " TEXT, "
                + Col_Password + " TEXT " +
                ")";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table);
        onCreate(db);
    }

    public void registerNewUser(String name, String pass){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_Username, name);
        values.put(Col_Password, pass);

        db.insert(DB_Table, null, values);

        db.close();
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM " + DB_Table +" ORDER BY "+ Col_ID +" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                User user = new User();
                user.setId(Long.parseLong(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));
                users.add(user);
            }while (cursor.moveToNext());
        }
        return users;
    }
}
