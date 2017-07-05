package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by mr on 18-03-2017.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>>
{
    private String url1;
    public NewsLoader(Context context,String url)
    {
        super(context);
        url1=url;
    }
    @Override
    protected void onStartLoading()
    {
        forceLoad();
    }
    @Override
    public List<News> loadInBackground()
    {
        if(url1==null)
        {
            return null;
        }
        List<News> res = NewsUtils.fetchNewsData(url1);
        return res;
    }
}
