package com.example.android.newsapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mr on 18-03-2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    public NewsAdapter(Context context, List<News> news)
    {
        super(context,0,news);
    }
    @Override
    public View getView(int position , View convertView, ViewGroup parent)
    {
        View listView = convertView;
        if(listView==null)
        {
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        News current =getItem(position);
        TextView sectionView =(TextView) listView.findViewById(R.id.text_view1);
        sectionView.setText(current.getSection());
        TextView newsView =(TextView) listView.findViewById(R.id.text_view2);
        newsView.setText(current.getNews());
        return listView;
    }
}
