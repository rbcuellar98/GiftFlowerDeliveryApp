package com.example.giftflowerdeliveryapp;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// --------------------------------------------------------------------
// Assignment 2
// Rolando Banasco Cuellar 1790333
// Application Development 2 (Mobile)
// Winter 2022
// --------------------------------------------------------------------

/**
 * This class adds decoration to the recyclerview grid
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final int space;
    // item spacing on recyclerView
    public SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    // item position on the screen
    public void getItemOffsets(Rect outRect, @NonNull View view,
                               RecyclerView parent, @NonNull RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }
}
