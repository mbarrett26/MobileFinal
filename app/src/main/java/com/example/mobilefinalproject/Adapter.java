package com.example.mobilefinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private List<itemModel> items;
    private List<itemModel> filteredItems;
    public static List<itemModel> cart;

    Adapter(Context context,List<itemModel> items, String category){
        this.context = context;
        this.items = items;
        filterItemsByCategory(category);
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
        Button addBtn;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameOutput = itemView.findViewById(R.id.itemName);
            descriptionOutput = itemView.findViewById(R.id.itemDescription);
            priceCalorieOutput = itemView.findViewById(R.id.itemPriceCalories);
            image = itemView.findViewById(R.id.itemImage);
            addBtn = itemView.findViewById(R.id.addItem);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemModel selectedItem = filteredItems.get(getAdapterPosition());
                    handelAddItem(selectedItem);
                }
            });
        }
    }

    private void handelAddItem(itemModel selectedItem) {
        int index = findItemIndex(selectedItem);
        if (index != -1) {
            // Item already exists, increment its quantity
            itemModel existingItem = filteredItems.get(index);
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            // New item, add it to the list with quantity 1
            selectedItem.setQuantity(1);
        }

        cart.add(selectedItem);
    }

    private int findItemIndex(itemModel selectedItem) {
        for (int i = 0; i < items.size(); i++) {
            itemModel currentItem = items.get(i);
            if (currentItem.equals(selectedItem)) {
                return i; // Return the index when the item is found
            }
        }
        return -1; // Return -1 if the item is not found
    }
}
