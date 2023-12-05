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
    private static final String Col_total_3 = "total";


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

    public void deleteDB(){ //function to delete the DB.
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_1);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_2);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_3);
        db.execSQL("DROP TABLE IF EXISTS " + DB_Table_4);
        onCreate(db);
    }

    // Method Add New User to the DB, using a model class
    public void addUser(User inputAcc){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Col_Username_1, inputAcc.getUsername().toLowerCase());
        values.put(Col_Password_1, inputAcc.getPassword());

        db.insert(DB_Table_1, null, values);

        db.close();
    }

    // Method to add a new review to the database
    public void addReview(Long userId, int rating, String reviewText){
        // Get a writable database instance
        SQLiteDatabase db = this.getWritableDatabase();

        // Create ContentValues to hold review data
        ContentValues values = new ContentValues();

        // Put review details into ContentValues
        values.put(Col_userid_4, userId); // User ID associated with the review
        values.put(Col_rating_4, rating); // Rating given in the review
        values.put(Col_reviewdata_4, reviewText); // Text content of the review

        // Insert the review data into the specified table
        db.insert(DB_Table_4, null, values);

        // Close the database connection
        db.close();
    }


    // Get Reviews from DB
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

    // Method to add a new menu item to the database
    public void addItem(itemModel input){
        // Get a writable database instance
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Put item details into ContentValues
        values.put(Col_name_2, input.getItemName()); // Name of the item
        values.put(Col_price_2, input.getPrice()); // Price of the item
        values.put(Col_image_2, input.getImage()); // Image associated with the item
        values.put(Col_description_2, input.getDescription()); // Description of the item
        values.put(Col_calories_2, input.getCalories()); // Calories of the item
        values.put(Col_category_2, input.getCategory()); // Category of the item

        // Insert the item data into the specified table
        db.insert(DB_Table_2, null, values);

        // Close the database connection
        db.close();
    }

    // Method to add an order to the user's order list in the database
    public void addOrder(orderModel input){
        // Get a writable database instance
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Put order details into ContentValues
        values.put(Col_userid_3, input.getUserID()); // User ID associated with the order
        values.put(Col_order_3, input.getOrderList()); // List of items in the order
        values.put(Col_total_3, input.getTotal()); // Total amount for the order

        // Insert the order data into the specified table
        db.insert(DB_Table_3, null, values);

        // Close the database connection
        db.close();
    }


    // Grab Order List
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

    // This method retrieves items from a database table named DB_Table_2, ordering them by Col_ID in descending order.
    public List<itemModel> getItems(){
        // Initialize a list to hold itemModel objects retrieved from the database.
        List<itemModel> items = new ArrayList<>();

        // Construct the query to select all items from the table and order them by Col_ID in descending order.
        String query = "SELECT * FROM " + DB_Table_2 +" ORDER BY "+ Col_ID +" DESC";

        // Get a readable database instance.
        SQLiteDatabase db = this.getReadableDatabase();

        // Execute the query and retrieve the result as a Cursor object.
        Cursor cursor = db.rawQuery(query,null);

        // Check if the cursor can move to the first row.
        if(cursor.moveToFirst()){
            // Iterate through the cursor to extract data and create itemModel objects.
            do{
                // Create a new itemModel object.
                itemModel item = new itemModel();

                // Set attributes of the itemModel object based on the cursor's data.
                item.setId(Long.parseLong(cursor.getString(0)));
                item.setItemName(cursor.getString(1));
                item.setPrice(Double.parseDouble(cursor.getString(2)));
                item.setImage(cursor.getBlob(3));
                item.setDescription(cursor.getString(4));
                item.setCalories(Integer.parseInt(cursor.getString(5)));
                item.setCategory(cursor.getString(6));

                // Add the itemModel object to the list.
                items.add(item);
            } while (cursor.moveToNext()); // Move to the next row in the cursor.
        }

        // Close the cursor and return the list of itemModel objects retrieved from the database.
        return items;
    }


    // Method to retrieve a list of users from the database
    public List<User> getUsers(){
        // Create a list to hold User objects
        List<User> users = new ArrayList<>();

        // Formulate the SQL query
        String query = "SELECT * FROM " + DB_Table_1 +" ORDER BY "+ Col_ID +" DESC";

        // Get a readable database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Execute the raw SQL query
        Cursor cursor = db.rawQuery(query,null);

        // Check if the cursor contains any data
        if(cursor.moveToFirst()){
            // Iterate through the cursor rows
            do{
                // Create a new User object
                User user = new User();

                // Set User object properties from cursor data
                user.setId(Long.parseLong(cursor.getString(0)));
                user.setUsername(cursor.getString(1));
                user.setPassword(cursor.getString(2));

                // Add User object to the list
                users.add(user);
            } while (cursor.moveToNext()); // Move to the next row in the cursor
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return the list of users
        return users;
    }


    // Method to retrieve user ID based on username
    public long getUserID(String nameInput){
        long userID = 0 ;
        String[] columns = {Col_ID};
        SQLiteDatabase db = this.getReadableDatabase();

        // Selection criteria
        String selection = Col_Username_1 + " = ?";
        // Selection argument
        String[] selectionArgs = {nameInput.toLowerCase()};

        // Query user table with condition
        Cursor cursor = db.query(DB_Table_1, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            userID = Long.parseLong(cursor.getString(0));
        }
        cursor.close();
        db.close();

        return userID;
    }

    // Method to retrieve username based on user ID
    public String getUserName(long idInput){
        String userName = "";
        String[] columns = {Col_Username_1};
        SQLiteDatabase db = this.getReadableDatabase();

        // Selection criteria
        String selection = Col_ID + " = ?";
        // Selection argument
        String[] selectionArgs = {String.valueOf(idInput)};

        // Query user table with condition
        Cursor cursor = db.query(DB_Table_1, columns, selection, selectionArgs, null, null, null);

        if(cursor.moveToFirst()){
            userName = cursor.getString(0);
        }
        cursor.close();
        db.close();

        return userName;
    }


    public boolean checkOrder(long userID){ //function to query the DB to check if a user made an Order
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
    public boolean checkUser(String nameInput) { //function to check if a username exists in the DB.
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

    // Method to check the existence of a user account in the database
    public boolean checkAcc(User acc) { //function to verify the account information in the database using a User model class.
        // Array of columns to fetch
        String[] columns = {Col_ID};

        // Get a readable database instance
        SQLiteDatabase db = this.getReadableDatabase();

        // Selection criteria for username and password
        String selection = Col_Username_1 + " = ?" + " AND " + Col_Password_1 + " = ?";

        // Selection arguments for the username and password
        String[] selectionArgs = {acc.getUsername().toLowerCase(), acc.getPassword()};

        // Query the user table with conditions
        Cursor cursor = db.query(
                DB_Table_1,     // Table to query
                columns,        // Columns to return
                selection,      // Columns for the WHERE clause
                selectionArgs,  // The values for the WHERE clause
                null,           // Group the rows
                null,           // Filter by row groups
                null            // The sort order
        );

        // Get the count of rows returned by the query
        int cursorCount = cursor.getCount();

        // Close the cursor and database
        cursor.close();
        db.close();

        // Return true if the query returned at least one row, indicating the existence of the account
        return cursorCount > 0;
    }
}


