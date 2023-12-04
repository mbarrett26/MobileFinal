package com.example.mobilefinalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

// Adapter for displaying images in a RecyclerView
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<Integer> images; // Use List<String> for image URLs or other types

    // Constructor for the ImageAdapter
    public ImageAdapter(List<Integer> images) {
        this.images = images;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflating the layout for individual image items
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false);
        return new ImageViewHolder(view); // Returning a new ImageViewHolder for the inflated view
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        // Retrieving the image resource at the current position
        int imageRes = images.get(position);
        // Setting the image resource to the ImageView in the ViewHolder
        holder.imageView.setImageResource(imageRes);
    }

    @Override
    public int getItemCount() {
        // Returning the total number of images in the dataset
        return images.size();
    }

    // ViewHolder class to hold the views for each individual item
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView; // ImageView to display the image

        // Constructor for the ViewHolder
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initializing the ImageView from the inflated item_image layout
            imageView = itemView.findViewById(R.id.imageView_item);
        }
    }
}

