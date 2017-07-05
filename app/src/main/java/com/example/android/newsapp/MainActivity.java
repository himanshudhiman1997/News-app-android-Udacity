package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    String url_news= "http://content.guardianapis.com/search?page-size=10&q=news&api-key=0fc305c0-c13b-40dd-b619-cc5149f58189";
    private NewsAdapter mAdapter=null;
    private TextView EmptyTextView=null;
    private int load_id=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView newsView = (ListView) findViewById(R.id.list_view);
        EmptyTextView = (TextView) findViewById(R.id.empty_id);
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        newsView.setAdapter(mAdapter);
        newsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                News current = mAdapter.getItem(position);
                Uri newsUri = Uri.parse(current.getUrl());
                Intent webnews = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(webnews);
            }
        });
        LoaderManager loaderManager = null;

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            EmptyTextView.setVisibility(View.GONE);
            loaderManager = getLoaderManager();
            loaderManager.initLoader(load_id, null, MainActivity.this);
        } else {
            View progressBar = findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.INVISIBLE);
            EmptyTextView.setText(getString(R.string.nc));
        }
    }
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle bundle) {
        return new NewsLoader(this,url_news);
    }
    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        ProgressBar progress = (ProgressBar) findViewById(R.id.progress_bar);
        progress.setVisibility(View.GONE);
        EmptyTextView.setText(getString(R.string.nu));
        mAdapter.clear();
        if (data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        } else EmptyTextView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        mAdapter.clear();
    }
}
