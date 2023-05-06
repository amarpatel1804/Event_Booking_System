package com.amarpatel.eventmanagement;

import android.app.Activity;
import android.app.Application;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class MyApplication extends Application {

    public static void setRecyclerItemMargin(Activity activity, RecyclerView recyclerView) {
        final int Top = activity.getResources().getDimensionPixelSize(R.dimen.recycler_top_6) / 2;
        final int Bottom = activity.getResources().getDimensionPixelSize(R.dimen.recycler_bottom_6) / 2;
        final int Left = activity.getResources().getDimensionPixelSize(R.dimen.recycler_left_6) / 2;
        final int Right = activity.getResources().getDimensionPixelSize(R.dimen.recycler_right_6) / 2;
        recyclerView.setPadding(Left,Top,Right,Bottom);
        recyclerView.setClipToPadding(false);
        recyclerView.setClipChildren(false);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(Left,Top,Right,Bottom);
            }
        });
    }
}
