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

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private List<itemModel> items;
    private List<itemModel> filteredItems;
    private List<itemModel> cart;

    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    Adapter(Context context,List<itemModel> items, String category){
        this.context = context;
        this.items = items;
        filterItemsByCategory(category);
        initCartFromSharedPreferences();
    }

    private void filterItemsByCategory(String category) {
        filteredItems = new ArrayList<>();
        for (itemModel item : items) {
            if (item.getCategory().equals(category)) {
                filteredItems.add(item);
            }
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        String name = filteredItems.get(position).getItemName();
        String price = "" + filteredItems.get(position).getPrice();
        byte[] imageByte = filteredItems.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte .length);
        String description = filteredItems.get(position).getDescription();
        String calories = "" + filteredItems.get(position).getCalories();

        holder.nameOutput.setText(name);
        holder.descriptionOutput.setText(description);
        holder.priceCalorieOutput.setText(String.format("$%s | %s Cals", price, calories));
        holder.image.setImageBitmap(bitmap);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentItem = holder.getAdapterPosition();
                itemModel selectedItem = filteredItems.get(currentItem);
                addToCart(selectedItem);
                Toast.makeText(context, selectedItem.getItemName() + " has been Added to Cart", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

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

    private void addToCart(itemModel selectedItem) {
        int index = findItemIndex(selectedItem);
        if (index != -1) {
            // Item already exists in cart, increment its quantity
            int incrementQuantity = cart.get(index).getQuantity() + 1;
            cart.get(index).setQuantity(incrementQuantity);
            Toast.makeText(context, cart.get(index).getItemName() + ", " + cart.get(index).getQuantity(), Toast.LENGTH_SHORT).show();
        } else {
            itemModel newItem = new itemModel(
                    selectedItem.getItemName(),
                    selectedItem.getPrice(),
                    selectedItem.getImage(),
                    selectedItem.getDescription(),
                    selectedItem.getCalories(),
                    selectedItem.getCategory()
            );
            newItem.setQuantity(1);
            Toast.makeText(context, newItem.getItemName() + ", " + newItem.getQuantity(), Toast.LENGTH_SHORT).show();
            cart.add(newItem);
        }

        saveCartToSharedPreferences();
    }

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

    private void initCartFromSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        String cartItemsJson = sharedPreferences.getString(CART_ITEMS_KEY, "");

        Log.d("SharedPreferences", "Retrieved JSON: " + cartItemsJson);

        if (!cartItemsJson.isEmpty()) {
            Gson gson = new Gson();
            cart = gson.fromJson(cartItemsJson, new TypeToken<List<itemModel>>(){}.getType());
            Log.d("SharedPreferences", "test: " + cart.get(0).getCalories());
        } else {
            cart = new ArrayList<>();
        }
    }

    // Save cart to SharedPreferences
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
