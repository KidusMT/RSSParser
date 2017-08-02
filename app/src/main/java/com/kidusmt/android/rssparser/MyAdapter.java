package com.kidusmt.android.rssparser;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;

/**
 * Created by KidusMT on 8/2/2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    ArrayList<FeedItem> feedItems;
    Context context;
    public MyAdapter(Context context, ArrayList<FeedItem> feedItems){
        this.feedItems = feedItems;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_row_news_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.Date.setText(current.getPubDate());
        //This is the Glide library for loading image from the URL on the internet
        Glide.with(context).load(current.getThumbnail())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.Thumbnail);
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Description, Date;
        ImageView Thumbnail;
        public MyViewHolder(View view) {
            super(view);
            Title = (TextView) view.findViewById(R.id.title);
            Description = (TextView) view.findViewById(R.id.description);
            Date = (TextView) view.findViewById(R.id.pub_date);
            Thumbnail = (ImageView) view.findViewById(R.id.thumbnail_img);
        }
    }
}
