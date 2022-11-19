package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.newsapp.models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class NewsDetail extends AppCompatActivity {
    NewsHeadlines headlines;
    TextView txt_title, txt_author, txt_time, txt_detail, txt_content;
    ImageView news_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        txt_title = findViewById(R.id.textDetailTitle);
        txt_author = findViewById(R.id.textDetailAuthor);
        txt_time = findViewById(R.id.textDetailTime);
        txt_detail = findViewById(R.id.textDetailDetail);
        txt_content = findViewById(R.id.textDetailContent);
        news_img=findViewById(R.id.imgDetailNews);

        headlines = (NewsHeadlines) getIntent().getSerializableExtra("headline");
        txt_title.setText(headlines.getTitle());
        txt_author.setText(headlines.getAuthor());
        txt_time.setText(headlines.getPublishedAt());
        txt_detail.setText(headlines.getDescription());
        txt_content.setText(headlines.getContent());
        Picasso.get().load(headlines.getUrlToImage()).into(news_img);
    }
}