package com.example.mobilefinalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private List<itemModel> filteredItems;
    private List<itemModel> cart;

    // JSON Keys
    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    // Constructor initializing the Adapter with context and items
    Adapter(Context context,List<itemModel> items){
        this.context = context;
        this.filteredItems = items;

        // Initializing cart from SharedPreferences
        initCartFromSharedPreferences();
    }

    // Inflating the layout for each item in the RecyclerView
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false));
    }

    // Binding data to the views in each RecyclerView item
    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        // Extracting item details
        String name = filteredItems.get(position).getItemName();
        String price = "" + filteredItems.get(position).getPrice();
        byte[] imageByte = filteredItems.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte .length);
        String description = filteredItems.get(position).getDescription();
        String calories = "" + filteredItems.get(position).getCalories();

        // Setting data to the views
        holder.nameOutput.setText(name);
        holder.descriptionOutput.setText(description);
        holder.priceCalorieOutput.setText(String.format("$%s | %s Cals", price, calories));
        holder.image.setImageBitmap(bitmap);

        // Handling add button click for adding items to the cart
        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = holder.getAdapterPosition();
                itemModel selectedItem = filteredItems.get(currentItem);
                addToCart(selectedItem);
            }
        });
    }

    // Getting the number of items in the RecyclerView
    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    // Filtering the list based on search query
    public void filterList(List<itemModel> filteredList) {
        this.filteredItems = filteredList;
        notifyDataSetChanged();
    }

    // ViewHolder class for the RecyclerView items
    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameOutput;
        TextView descriptionOutput;
        TextView priceCalorieOutput;
        ImageView image;
        ImageButton addBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameOutput = itemView.findViewById(R.id.itemName);
            descriptionOutput = itemView.findViewById(R.id.itemDescription);
            priceCalorieOutput = itemView.findViewById(R.id.itemPriceCalories);
            image = itemView.findViewById(R.id.itemImage);
            addBtn = itemView.findViewById(R.id.addItem);
        }
    }

    // Adding an item to the cart
    private void addToCart(itemModel selectedItem) {
        int index = findItemIndex(selectedItem);
        if (index != -1) {
            // Item already exists in cart, increment its quantity
            int incrementQuantity = cart.get(index).getQuantity() + 1;
            cart.get(index).setQuantity(incrementQuantity);
        } else {
            // Item doesn't exist in cart, add it with quantity 1
            itemModel newItem = new itemModel(
                    selectedItem.getItemName(),
                    selectedItem.getPrice(),
                    selectedItem.getImage(),
                    selectedItem.getDescription(),
                    selectedItem.getCalories(),
                    selectedItem.getCategory()
            );
            newItem.setQuantity(1);
            Toast.makeText(context, newItem.getItemName() + " has Been Added to Cart", Toast.LENGTH_LONG).show();
            cart.add(newItem);
        }

        // Save the updated cart to SharedPreferences
        saveCartToSharedPreferences();
    }

    // Finding the index of an item in the cart
    private int findItemIndex(itemModel selectedItem) {
        if (cart != null) {
            for (int i = 0; i < cart.size(); i++) {
                itemModel currentItem = cart.get(i);
                if (currentItem.getItemName().equals(selectedItem.getItemName())) {
                    return i; // Return the index when the item name matches
                }
            }
        }
        return -1; // Return -1 if the item is not found
    }

    // Initializing the cart from SharedPreferences
    private void initCartFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String cartItemsJson = sharedPreferences.getString(CART_ITEMS_KEY, "");

        Log.d("SharedPreferences", "Retrieved JSON: " + cartItemsJson);

        if (!cartItemsJson.isEmpty() && cartItemsJson.trim().length() > 4) {
            Gson gson = new Gson();
            cart = gson.fromJson(cartItemsJson, new TypeToken<List<itemModel>>(){}.getType());
            Log.d("SharedPreferences", "test: " + cart.get(0).getCalories());
        } else {
            cart = new ArrayList<>();
        }
    }

    // Saving the cart to SharedPreferences
    private void saveCartToSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String cartItemsJson = gson.toJson(cart);

        Log.d("SharedPreferences", "Saved JSON: " + cartItemsJson);

        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }
}
