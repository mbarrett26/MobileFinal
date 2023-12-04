package com.example.mobilefinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder> {
    private Context context;
    private List<orderModel> orderList;

    // Constructor to initialize the adapter with context and orderList
    orderAdapter(Context context, List<orderModel> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view by inflating the order_layout XML
        return new orderAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull orderAdapter.MyViewHolder holder, int position) {
        // Get the order information at the given position
        String items = "Order Items: " + orderList.get(position).getOrderList();
        String price = "Order Price: $" + String.format("%.2f", orderList.get(position).getTotal());

        // Set the text to the TextViews in the layout
        holder.orderItems.setText(items);
        holder.orderPrice.setText(price);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return orderList.size();
    }

    // Inner ViewHolder class to hold the views
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderItems;
        TextView orderPrice;

        // Constructor to bind views to their respective objects
        public MyViewHolder(View itemView) {
            super(itemView);
            orderItems = itemView.findViewById(R.id.orderList); // Initialize order items TextView
            orderPrice = itemView.findViewById(R.id.orderPrice); // Initialize order price TextView
        }
    }
}

