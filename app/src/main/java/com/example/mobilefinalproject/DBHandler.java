package com.example.mobilefinalproject;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private static final String DB_Name = "restaurantDb";
    private static final int DB_Version = 1;

    public DBHandler(Context context) {

        super(context, DB_Name, null, DB_Version);
    }

    private static final String DB_Table_1 = "userData";
    private static final String Col_ID = "id";
    private static final String Col_Username_1 = "username";
    private static final String Col_Password_1 = "password";

    private static final String DB_Table_2 = "resturantItemData";
    private static final String Col_name_2 = "itemname";
    private static final String Col_price_2 = "price";
    private static final String Col_image_2 = "image";
    private static final String Col_description_2 = "description";
    private static final String Col_calories_2 = "calories";
    private static final String Col_category_2 = "category";

    private static final String DB_Table_3 = "orderData";
    private static final String Col_userid_3 = "userid";
    private static final String Col_order_3 = "userorders";
    private static final String Col_total_3 = "total"; //could also add location after


    private static final String DB_Table_4 = "reviewData";
    private static final String Col_userid_4 = "userid";
    private static final String Col_rating_4 = "rating";
    private static final String Col_reviewdata_4 = "reviewText";

    @Override
    public void onCreate(SQLiteDatabase db) {
        String user_query = "CREATE TABLE " + DB_Table_1 + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_Username_1 + " TEXT, "
                + Col_Password_1 + " TEXT " +
                ")";
        db.execSQL(user_query);

        user_query = "CREATE TABLE " + DB_Table_2 + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_name_2 + " TEXT, "
                + Col_price_2 + " TEXT, "
                + Col_image_2 + " TEXT, "
                + Col_description_2 + " TEXT, "
                + Col_calories_2 + " TEXT, "
                + Col_category_2 + " TEXT " +
                ")";
        db.execSQL(user_query);

        user_query = "CREATE TABLE " + DB_Table_3 + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_userid_3 + " TEXT, "
                + Col_order_3 + " JSON, "
                + Col_total_3 + " TEXT " +
                ")";
        db.execSQL(user_query);

        user_query = "CREATE TABLE " + DB_Table_4 + " ("
                + Col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Col_userid_4 + " INTEGER, "
                + Col_rating_4 + " INTEGER, "
                + Col_reviewdata_4 + " TEXT " +
                ")";
        db.execSQL(user_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_1);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_2);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_3);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_4);
        onCreate(db);
    }

    public void deleteDB(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_1);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_2);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_3);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_4);
        onCreate(db);
    }

    public void addUser(User inputAcc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_Username_1, inputAcc.getUsername().toLowerCase());
        values.put(Col_Password_1, inputAcc.getPassword());

        db.insert(DB_Table_1, null, values);

        db.close();
    }

    public void addReview(Long userId,int rating ,String reviewText){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_userid_4, userId);
        values.put(Col_rating_4,rating);
        values.put(Col_reviewdata_4, reviewText);

        db.insert(DB_Table_4, null, values);

        db.close();
    }

    public List<reviewModel> getReview(int rating){
        List<reviewModel> reviews = new ArrayList<>();

        // array of columns to fetch
        String[] columns = {
                Col_userid_4,Col_rating_4,Col_reviewdata_4
        };
        SQLiteDatabase db = this.getReadableDatabase();


        // selection criteria
        String selection = Col_rating_4 + " = ?";
        // selection arguments
        String[] selectionArgs = {String.valueOf(rating)};
        // query user table with conditions

        Cursor cursor = db.query(DB_Table_4, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        if(cursor.moveToFirst()){
            do{
                reviewModel revi = new reviewModel();

                revi.setUsername(getUserName(cursor.getLong(0)));
                Log.d("DbCheck", "username: " + revi.getUsername());
                revi.setRating(Integer.parseInt(cursor.getString(1)));
                revi.setReviewText(cursor.getString(2));

                reviews.add(revi);

            }while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return reviews;
    }

    public void addItem(itemModel input){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_name_2, input.getItemName());
        values.put(Col_price_2, input.getPrice());
        values.put(Col_image_2, input.getImage());
        values.put(Col_description_2, input.getDescription());
        values.put(Col_calories_2, input.getCalories());
        values.put(Col_category_2, input.getCategory());

        db.insert(DB_Table_2, null, values);

        db.close();
    }
    public void addOrder(orderModel input){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_userid_3, input.getUserID());
        values.put(Col_order_3, input.getOrderList());
        values.put(Col_total_3, input.getTotal());

        db.insert(DB_Table_3, null, values);

        db.close();
    }

    public List<orderModel> getOrders(long id){
        List<orderModel> orders = new ArrayList<>();

        // array of columns to fetch
        String[] columns = {
                Col_userid_3,Col_order_3,Col_total_3
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_userid_3 + " = ?";
        // selection arguments
        String[] selectionArgs = {String.valueOf(id)};
        // query user table with conditions

        Cursor cursor = db.query(DB_Table_3, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        if(cursor.moveToFirst()){
            do{
                orderModel order = new orderModel();
                order.setUserID(Long.parseLong(cursor.getString(0)));
                order.setOrderList(cursor.getString(1));
                order.setTotal(Double.parseDouble(cursor.getString(2)));
                orders.add(order);

            }while (cursor.moveToNext());
        }


        cursor.close();
        db.close();

        return orders;
    }

    public List<itemModel> getItems(){
        List<itemModel> items = new ArrayList<>();
        String query = "SELECT * FROM " + DB_Table_2 +" ORDER BY "+ Col_ID +" DESC";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                itemModel item = new itemModel();
                item.setId(Long.parseLong(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setPrice(Double.parseDouble(cursor.getString(2)));
                item.setImage(cursor.getBlob(3));
                item.setDescription(cursor.getString(4));
                item.setCalories(Integer.parseInt(cursor.getString(5)));
                item.setCategory(cursor.getString(6));
                items.add(item);
            }while (cursor.moveToNext());
        }
        return items;
    }

    public List<User> getUsers(){
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM " + DB_Table_1 +" ORDER BY "+ Col_ID +" DESC";
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

    public long getUserID(String nameInput){

        long userID =0 ;

        String[] columns = {
                Col_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_Username_1 + " = ?";
        // selection argument
        String[] selectionArgs = {nameInput.toLowerCase()};
        // query user table with condition

        Cursor cursor = db.query(DB_Table_1, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if(cursor.moveToFirst()){
            userID = Long.parseLong(cursor.getString(0));
        }
        cursor.close();
        db.close();

        return userID;
    }

    public String getUserName(long idInput){

        String userName = "";

        String[] columns = {
                Col_Username_1
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_ID + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(idInput)};
        // query user table with condition

        Cursor cursor = db.query(DB_Table_1, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order

        if(cursor.moveToFirst()){
            userName = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return userName;
    }

    public boolean checkOrder(long userID){
        String[] columns = {
                Col_userid_3
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_userid_3 + " = ?";
        // selection argument
        String[] selectionArgs = {String.valueOf(userID)};
        // query user table with condition

        Cursor cursor = db.query(DB_Table_3, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();
        return cursorCount > 0;
    }
    public boolean checkUser(String nameInput) {
        // array of columns to fetch
        String[] columns = {
                Col_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_Username_1 + " = ?";
        // selection argument
        String[] selectionArgs = {nameInput.toLowerCase()};
        // query user table with condition

        Cursor cursor = db.query(DB_Table_1, //Table to query
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

    public boolean checkAcc(User acc) {
        // array of columns to fetch
        String[] columns = {
                Col_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = Col_Username_1 + " = ?" + " AND " + Col_Password_1 + " = ?";
        // selection arguments
        String[] selectionArgs = {acc.getUsername().toLowerCase(), acc.getPassword()};
        // query user table with conditions

        Cursor cursor = db.query(DB_Table_1, //Table to query
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


