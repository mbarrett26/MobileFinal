package com.example.mobilefinalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.MyViewHolder> {
    private Context context;
    private List<itemModel> cart;

    // JSON Keys
    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    // Constructor initializing the Adapter with context
    cartAdapter(Context context) {
        this.context = context;

        // Initializing cart from SharedPreferences
        initCartFromSharedPreferences();
    }

    // Initializing the cart from SharedPreferences
    private void initCartFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String cartItemsJson = sharedPreferences.getString(CART_ITEMS_KEY, "");

        if (!cartItemsJson.isEmpty() && cartItemsJson.trim().length() > 4) {
            Gson gson = new Gson();
            cart = gson.fromJson(cartItemsJson, new TypeToken<List<itemModel>>(){}.getType());
        } else {
            cart = new ArrayList<>();
        }

        //Log.d("SharedPreferences","The Cart" + cart.get(0).getCalories());
    }

    public double calculateTotalPrice() {
        // Calculate the total price of items in the cart
        double subtotal = 0.00;
        for (itemModel item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for each item in the RecyclerView
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind data to the views in each RecyclerView item for the cart
        // Extract item details
        String name = cart.get(position).getItemName();
        String quantity = "" + cart.get(position).getQuantity();
        byte[] imageByte = cart.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte .length);

        // Set data to the views
        holder.image.setImageBitmap(bitmap);
        holder.itemName.setText(name);
        holder.itemPrice.setText(String.format("%.2f", cart.get(position).getPrice() * cart.get(position).getQuantity()));
        holder.itemQuantity.setText(quantity);

        // Handle incrementing quantity when the add button is clicked
        int index = holder.getAdapterPosition();
        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Increment item quantity and update views
                int incrementQuantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(incrementQuantity);

                // Update quantity and price in the views
                String quantity = "" + cart.get(index).getQuantity();
                holder.itemQuantity.setText(quantity);
                holder.itemPrice.setText(String.format("%.2f", cart.get(index).getPrice() * cart.get(index).getQuantity()));

                // Notify of quantity change and save to SharedPreferences
                notifyQuantityChanged();
                saveCartToSharedPreferences();
            }
        });

        // Handle decrementing quantity when the minus button is clicked
        holder.minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Decrement item quantity and update views
                if (index != RecyclerView.NO_POSITION) {
                    int currentQuantity = cart.get(index).getQuantity();
                    if (currentQuantity > 1) {
                        int decrementQuantity = cart.get(index).getQuantity() - 1;
                        cart.get(index).setQuantity(decrementQuantity);
                        holder.itemPrice.setText(String.format("%.2f", cart.get(index).getPrice() * cart.get(index).getQuantity()));

                        String quantity = "" + cart.get(index).getQuantity();
                        holder.itemQuantity.setText(quantity);
                    } else {
                        removeItem(index);
                    }

                    // Notify of quantity change and save to SharedPreferences
                    notifyQuantityChanged();
                    saveCartToSharedPreferences();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        // Return the number of items in the cart
        return cart.size();
    }

    // ViewHolder class for the RecyclerView items in the cart
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // Views in each item of the cart RecyclerView
        ImageView image;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;
        ImageButton addQuantity;
        ImageButton minusQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            // Initialize views
            image = itemView.findViewById(R.id.cItemImage);
            itemName = itemView.findViewById(R.id.cItemName);
            itemPrice = itemView.findViewById(R.id.cItemPrice);
            itemQuantity = itemView.findViewById(R.id.cItemQuantity);
            addQuantity = itemView.findViewById(R.id.addQuantity);
            minusQuantity = itemView.findViewById(R.id.minusQuantity);
        }
    }

    // Interface for notifying quantity changes
    public interface QuantityChangeListener {
        void onQuantityChanged();
    }

    private QuantityChangeListener quantityChangeListener;

    public void setQuantityChangeListener(QuantityChangeListener listener) {
        this.quantityChangeListener = listener;
    }

    private void notifyQuantityChanged() {
        if (quantityChangeListener != null) {
            quantityChangeListener.onQuantityChanged();
        }
    }

    // Get all item names in the cart
    public List<String> getAllItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (itemModel item : cart) {
            itemNames.add(item.getItemName());
        }
        return itemNames;
    }

    // Remove an item from the cart
    public void removeItem(int position) {
        if (position >= 0 && position < cart.size()) {
            cart.remove(position);
            notifyDataSetChanged();
        }
        notifyItemRemoved(position);
    }

    // Save the cart to SharedPreferences
    private void saveCartToSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String cartItemsJson = gson.toJson(cart);

        Log.d("SharedPreferences", "Saved JSON: " + cartItemsJson);

        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }

    // Clear the cart data in SharedPreferences
    public void clearCartInSharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CART_ITEMS_KEY, "");
        editor.apply();
    }
}

