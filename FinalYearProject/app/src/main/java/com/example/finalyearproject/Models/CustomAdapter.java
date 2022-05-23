package com.example.finalyearproject.Models;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private Context context;
    private List<NewsHeadlines> newsHeadlines;

    public CustomAdapter(Context context, List<NewsHeadlines> newsHeadlines) {
        this.context = context;
        this.newsHeadlines = newsHeadlines;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomViewHolder(LayoutInflater.from(context).inflate(R.layout.row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.text_title.setText(newsHeadlines.get(position).getTitle());
        holder.text_source.setText(newsHeadlines.get(position).getSource().getName());

        if(newsHeadlines.get(position).getUrlToImage() != null){
            Picasso.get().load(newsHeadlines.get(position).getUrlToImage()).into(holder.img_headline);
        }

    }

    @Override
    public int getItemCount() {
        return newsHeadlines.size();
    }
}
