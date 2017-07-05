package com.example.android.newsapp;

/**
 * Created by mr on 18-03-2017.
 */

public class News {
    private String Section1;
    private String News1;
    private String Url1;
    public News(String section,String news,String url){
        Section1=section;
        News1=news;
        Url1=url;
    }
    public String getSection(){
        return Section1;
    }
    public String getNews(){
        return News1;
    }
    public String getUrl(){
        return Url1;
    }
}
