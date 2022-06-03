package com.example.finalyearproject.Models;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalyearproject.R;

public class CustomViewHolder extends RecyclerView.ViewHolder {

    TextView text_title, text_source;
    ImageView img_headline;
    CardView cardView;
    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);

        //Getting an id from layout
        text_title = itemView.findViewById(R.id.input_text);
        text_source = itemView.findViewById(R.id.input_description);
        img_headline = itemView.findViewById(R.id.img_headline);
        cardView = itemView.findViewById(R.id.report_container);

    }
}
