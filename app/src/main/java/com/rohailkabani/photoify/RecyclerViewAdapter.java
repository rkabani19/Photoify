package com.rohailkabani.photoify;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.FlickrImageViewHolder> { //only view holders allowed to be used
    private static final String TAG = "RecyclerViewAdapter";
    private static List<Photo> photoList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Photo> photoList) {
        this.context = context;
        this.photoList = photoList;
    }

    @Override
    public FlickrImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Called by layout manager when it needs a new view
        Log.d(TAG, "onCreateViewHolder: New View Requested.");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.browse, parent, false);

        return new FlickrImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FlickrImageViewHolder holder, int position) {
        if ((photoList == null) || (photoList.size() == 0)) {
            holder.thumbnail.setImageResource(R.drawable.img_placeholder);
            holder.title.setText(R.string.placeholder_empty);
        } else {
            //Called by the layout manager when it wants new data in an existing row
            Photo photoItem = photoList.get(position);
            Picasso.with(context).load(photoItem.getImage())
                    .error(R.drawable.img_placeholder)
                    .placeholder(R.drawable.img_placeholder)
                    .into(holder.thumbnail);

            holder.title.setText(photoItem.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: Called.");
        return ((photoList != null) && (photoList.size() != 0) ? photoList.size(): 1);
    }

    void loadNewData (List<Photo> newPhoto) {
        photoList = newPhoto;
        notifyDataSetChanged();
    }

    public static Photo getPhoto(int position) {
        return ((photoList != null) && (photoList.size()) != 0 ? photoList.get(position) : null);
    }

    static class FlickrImageViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "FlickrImageViewHolder";
        ImageView thumbnail = null;
        TextView title = null;

        public FlickrImageViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "FlickrImageViewHolder: Constructor called.");
            this.thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
