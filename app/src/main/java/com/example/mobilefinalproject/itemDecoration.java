package com.example.mobilefinalproject;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Custom ItemDecoration class to add spacing between RecyclerView items
public class itemDecoration extends RecyclerView.ItemDecoration {
    private final int space; // Spacing value

    // Constructor to set the spacing value
    public itemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        // Setting left, right, bottom spacing for all items
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = space; // Apply top spacing for the first item
        } else {
            outRect.top = 0; // No top spacing for subsequent items
        }
    }
}
