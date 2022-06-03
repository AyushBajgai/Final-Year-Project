package com.example.finalyearproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class showNews extends AppCompatActivity {

    TextView text_content, text_headline;
    ImageView img;

    String title, desc, content, imageURL, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        text_content = findViewById(R.id.news_content);
        text_headline = findViewById(R.id.news_headline);
        img = findViewById(R.id.img_headline);

        text_content.setText(desc);
        text_headline.setText(title);
        Picasso.get().load(imageURL);
    }
}