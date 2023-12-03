package com.example.mobilefinalproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class reviewAdapter extends RecyclerView.Adapter<reviewAdapter.MyViewHolder>{
    private Context context;
    private List<reviewModel> reviews;

    reviewAdapter(Context context, List<reviewModel> reviews){
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new reviewAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull reviewAdapter.MyViewHolder holder, int position) {
        String user = reviews.get(position).getUsername();
        int rating = reviews.get(position).getRating();
        String review = reviews.get(position).getReviewText();

        Log.d("DbCheck", "Check: " + reviews.get(position).getUsername() + " and " + reviews.get(position).getRating());


        holder.reviewNameRating.setText(user + "   |   " + rating + " stars");
        holder.review.setText(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reviewNameRating;
        TextView review;

        public MyViewHolder(View itemView) {
            super(itemView);
            reviewNameRating = itemView.findViewById(R.id.reviewNameRating);
            review = itemView.findViewById(R.id.review);
        }
    }

    public void refresh(List<reviewModel> newList){
        this.reviews = newList;
        notifyDataSetChanged();
    }
}
