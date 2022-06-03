package com.example.finalyearproject.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;
import com.example.finalyearproject.showNews;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomViewHolder>{
    private NewsHeadlines newLine;
    private Context context;
    private List<NewsHeadlines> newsHeadlines;
    private SelectListener listener;
    showNews showNews;

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
    public void onBindViewHolder(@NonNull CustomViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.text_title.setText(newsHeadlines.get(position).getTitle());
        holder.text_source.setText(newsHeadlines.get(position).getSource().getName());

        if(newsHeadlines.get(position).getUrlToImage() != null){
            Picasso.get().load(newsHeadlines.get(position).getUrlToImage()).into(holder.img_headline);
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*              Intent i = new Intent(context, showNews.class);
                i.putExtra("title",newLine.getTitle());
                i.putExtra("content",newLine.getContent());
                i.putExtra("desc",newLine.getTitle());
                i.putExtra("image",newLine.getUrlToImage());
                i.putExtra("url",newLine.getUrl());
                context.startActivity(i);*/
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsHeadlines.size();
    }
}
