package com.example.mobilefinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private Context context;
    private List<itemModel> items;

    Adapter(Context context,List<itemModel> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.custom_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.MyViewHolder holder, int position) {
        String name = items.get(position).getItemName();
        String price = "$" + items.get(position).getPrice();

        holder.nameOutput.setText(name);
        holder.priceOutput.setText(price);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nameOutput;
        TextView priceOutput;
        ImageView image;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameOutput = itemView.findViewById(R.id.itemName);
            priceOutput = itemView.findViewById(R.id.itemPrice);
            image = itemView.findViewById(R.id.itemImage);
        }
    }
}
