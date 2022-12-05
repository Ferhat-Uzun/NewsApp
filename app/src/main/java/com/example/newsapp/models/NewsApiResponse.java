package com.example.newsapp.models;

import java.io.Serializable;
import java.util.List;

public class NewsApiResponse implements Serializable {
    String status;
    int totalResults;
    List<NewsHeadlines> articles;


    public List<NewsHeadlines> getArticles() {
        return articles;
    }

}
