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

    private static final String CART_PREFS = "cart_prefs";
    private static final String CART_ITEMS_KEY = "cart_items";

    cartAdapter(Context context) {
        this.context = context;
        initCartFromSharedPreferences();
    }

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
        double subtotal = 0.00;
        for (itemModel item : cart) {
            subtotal += item.getPrice() * item.getQuantity();
        }
        return subtotal;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String name = cart.get(position).getItemName();
        String quantity = "" + cart.get(position).getQuantity();
        byte[] imageByte = cart.get(position).getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte .length);

        holder.image.setImageBitmap(bitmap);
        holder.itemName.setText(name);
        holder.itemPrice.setText(String.format("%.2f", cart.get(position).getPrice() * cart.get(position).getQuantity()));
        holder.itemQuantity.setText(quantity);

        int index = holder.getAdapterPosition();
        holder.addQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int incrementQuantity = cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(incrementQuantity);

                String quantity = "" + cart.get(index).getQuantity();
                holder.itemQuantity.setText(quantity);
                holder.itemPrice.setText(String.format("%.2f", cart.get(index).getPrice() * cart.get(index).getQuantity()));
                notifyQuantityChanged();
                saveCartToSharedPreferences();
            }
        });

        holder.minusQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    notifyQuantityChanged();
                    saveCartToSharedPreferences();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cart.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView itemName;
        TextView itemPrice;
        TextView itemQuantity;

        ImageButton addQuantity;
        ImageButton minusQuantity;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cItemImage);
            itemName = itemView.findViewById(R.id.cItemName);
            itemPrice = itemView.findViewById(R.id.cItemPrice);
            itemQuantity = itemView.findViewById(R.id.cItemQuantity);

            addQuantity = itemView.findViewById(R.id.addQuantity);
            minusQuantity = itemView.findViewById(R.id.minusQuantity);

        }
    }

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

    public List<String> getAllItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (itemModel item : cart) {
            itemNames.add(item.getItemName());
        }
        return itemNames;
    }

    public void removeItem(int position) {
        if (position >= 0 && position < cart.size()) {
            cart.remove(position);
            notifyDataSetChanged();
        }
        notifyItemRemoved(position);

    }

    private void saveCartToSharedPreferences() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        String cartItemsJson = gson.toJson(cart);

        Log.d("SharedPreferences", "Saved JSON: " + cartItemsJson);

        editor.putString(CART_ITEMS_KEY, cartItemsJson);
        editor.apply();
    }

    public void clearCartInSharedPreferences(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(CART_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(CART_ITEMS_KEY, "");
        editor.apply();
    }

}

