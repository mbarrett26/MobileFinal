package com.example.mobilefinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder>{
    private Context context;
    private List<orderModel> orderList;

    orderAdapter(Context context, List<orderModel> orderList){
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new orderAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull orderAdapter.MyViewHolder holder, int position) {
        String items = "Order Items: " + orderList.get(position).getOrderList();
        String price = "Order Price: " + orderList.get(position).getTotal();

        holder.orderItems.setText(items);
        holder.orderPrice.setText(price);
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView orderItems;
        TextView orderPrice;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderItems = itemView.findViewById(R.id.orderList);
            orderPrice = itemView.findViewById(R.id.orderPrice);
        }
    }
}
