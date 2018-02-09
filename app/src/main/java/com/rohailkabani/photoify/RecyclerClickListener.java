package com.rohailkabani.photoify;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class RecyclerClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private static final String TAG = "RecyclerClickListener";

    interface OnRecyclerClickListener {
        void onItemClick(View view, int positon);
        void onItemLongClick(View view, int positon);
    }

    private final  OnRecyclerClickListener listener;
    private final GestureDetectorCompat gestureDetectorCompat;

    public RecyclerClickListener(Context context, /*final*/ RecyclerView recyclerView, OnRecyclerClickListener listener) {
        this.listener = listener;
        this.gestureDetectorCompat = new GestureDetectorCompat(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.d(TAG, "onInterceptTouchEvent: Starts.");
        if (gestureDetectorCompat != null) {
            boolean result = gestureDetectorCompat.onTouchEvent(e);
            Log.d(TAG, "onInterceptTouchEvent: Returned " + result);
            return result;
        } else {
            Log.d(TAG, "onInterceptTouchEvent: Returned False.");
            return false;
        }
    }
}
