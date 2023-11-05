package com.example.mobilefinalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_Name = "restaurantDb";
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
        String user_query = "CREATE TABLE " + DB_Table + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_Username + " TEXT, "
                + Col_Password + " TEXT " +
                ")";

        db.execSQL(user_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table);
        onCreate(db);
    }

    public void addUser(User inputAcc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_Username, inputAcc.getUsername().toLowerCase());
        values.put(Col_Password, inputAcc.getPassword());

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

    public boolean checkUser(String nameInput) {
        // array of columns to fetch
        String[] columns = {
                Col_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_Username + " = ?";
        // selection argument
        String[] selectionArgs = {nameInput.toLowerCase()};
        // query user table with condition

        Cursor cursor = db.query(DB_Table, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
    /**
     * This method to check user exist or not
     *
     * @param acc
     * @return true/false
     */
    public boolean checkAcc(User acc) {
        // array of columns to fetch
        String[] columns = {
                Col_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_Username + " = ?" + " AND " + Col_Password + " = ?";
        // selection arguments
        String[] selectionArgs = {acc.getUsername().toLowerCase(), acc.getPassword()};
        // query user table with conditions

        Cursor cursor = db.query(DB_Table, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }
        return false;
    }
}


