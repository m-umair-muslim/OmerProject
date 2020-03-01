package com.comsats.ars.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.comsats.ars.R;
import com.comsats.ars.data.FSItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ImageViewHolder> {

    private Context mContext;
    private List<FSItem> mItems;
    private SimpleDateFormat formater;

    public ImageListAdapter(Context context) {
        mContext = context;
        mItems = new ArrayList<>();
        formater = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS", Locale.getDefault());
    }

    public void updateList(List<FSItem> items) {
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
        FSItem item = mItems.get(position);
        Glide.with(mContext)
                .load(item.uri)
                .into(holder.imageView);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(item.creationDate);
        holder.textView.setText(formater.format(calendar.getTime()));
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
