package com.example.mac.newsapp;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by mac on 24/08/17.
 */

public class NewsAsyncTaskLoader extends AsyncTaskLoader<List<News>> {

    private String mUrl;


    public NewsAsyncTaskLoader(Context context,String url) {
        super(context);
        this.mUrl=url;
    }

    @Override
    public List<News> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        List<News> bookItems = null;
        // Perform the network request, parse the response, and extract a list of earthquakes.
        bookItems = QueryUtils.fetchNewsList(mUrl);
        return bookItems;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}


