package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.newsapp.models.NewsApiResponse;
import com.example.newsapp.models.NewsHeadlines;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener , View.OnClickListener {

    RecyclerView recyclerView;
    CustomAdapter adapter;
    ProgressDialog progressDialog;
    Button btn_business,btn_entertainment,btn_general,btn_health,btn_science,btn_sports,btn_technology;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView=findViewById(R.id.src_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                RequestManager requestManager = new RequestManager(MainActivity.this);
                requestManager.getNewsHeadlines(listener, "general", query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()){
                    RequestManager requestManager = new RequestManager(MainActivity.this);
                    requestManager.getNewsHeadlines(listener, "general", null);
                }else{
                    RequestManager requestManager = new RequestManager(MainActivity.this);
                    requestManager.getNewsHeadlines(listener, "general", query);
                }
                return true;
            }
        });

        btn_business=findViewById(R.id.btn_business);
        btn_entertainment=findViewById(R.id.btn_entertainment);
        btn_general=findViewById(R.id.btn_general);
        btn_health=findViewById(R.id.btn_health);
        btn_science=findViewById(R.id.btn_science);
        btn_sports=findViewById(R.id.btn_sports);
        btn_technology=findViewById(R.id.btn_technology);

        btn_business.setOnClickListener(this);
        btn_entertainment.setOnClickListener(this);
        btn_general.setOnClickListener(this);
        btn_health.setOnClickListener(this);
        btn_science.setOnClickListener(this);
        btn_sports.setOnClickListener(this);
        btn_technology.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Haberler yükleniyor");
        progressDialog.show();

        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadlines(listener, "general", null);

    }
    private final OnFetchDataListener<NewsApiResponse> listener = new OnFetchDataListener<NewsApiResponse>() {
        @Override
        public void onFetchData(List<NewsHeadlines> list, String message) {
           if (list.isEmpty()){
               Toast.makeText(MainActivity.this,"Veri Bulunamadı",Toast.LENGTH_LONG).show();
           }
           else{
               recyclerView = findViewById(R.id.recyler_main);
               recyclerView.setHasFixedSize(true);
               recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this,1));
               adapter = new CustomAdapter(MainActivity.this,list,MainActivity.this);
               recyclerView.setAdapter(adapter);
               progressDialog.dismiss();
           }
        }

        @Override
        public void onError(String message) {
            Toast.makeText(MainActivity.this,message,Toast.LENGTH_LONG).show();
        }
    };

    @Override
    public void OnNewsClicked(NewsHeadlines newsHeadlines) {
        startActivity(new Intent(MainActivity.this,NewsDetail.class).putExtra("headline",newsHeadlines));
    }

    @Override
    public void onClick(View view) {
        Button btn = (Button) view;

        RequestManager requestManager = new RequestManager(this);
        requestManager.getNewsHeadlines(listener, btn.getText().toString(), null);
    }

}