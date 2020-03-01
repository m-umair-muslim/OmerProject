package com.comsats.ars.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comsats.ars.R;

import java.util.ArrayList;
import java.util.List;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Uri> mItems;

    public ImageListAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
    }

    public void updateList(List<Uri> items) {
        this.mItems = items;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_image_view, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Glide.with(mContext)
                .load(mItems.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ImageViewHolder(View view) {
            super(view);

            imageView = (ImageView) view.findViewById(R.id.image_view_adapter_image);
            textView = (TextView) view.findViewById(R.id.image_view_adapter_text);
        }
    }

}
