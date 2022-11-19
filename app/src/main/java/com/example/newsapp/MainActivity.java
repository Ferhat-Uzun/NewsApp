package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.example.newsapp.models.NewsApiResponse;
import com.example.newsapp.models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener{

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Haberler yükleniyor");
        progressDialog.show();
        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadlines(listener, "general", null);

    }
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
            recyclerView = findViewById(R.id.recyler_main);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
            adapter = new CustomAdapter(MainActivity.this,list,MainActivity.this);
            recyclerView.setAdapter(adapter);
            progressDialog.dismiss();
        }

        @Override
        public void onError(String message) {

        }
    };

    @Override
    public void OnNewsClicked(NewsHeadlines newsHeadlines) {
        startActivity(new Intent(MainActivity.this,NewsDetail.class).putExtra("headline",newsHeadlines));
    }

    /*private void showNews(List<NewsHeadlines> list) {
        recyclerView = findViewById(R.id.recyler_main);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        adapter = new CustomAdapter(this,list);
        recyclerView.setAdapter(adapter);
    }*/
}