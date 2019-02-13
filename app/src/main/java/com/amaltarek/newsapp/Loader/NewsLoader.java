package com.amaltarek.newsapp.Loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.amaltarek.newsapp.Model.News;
import com.amaltarek.newsapp.Utils.QueryUtils;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    public NewsLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<News> loadInBackground() {
        // Perform the network request, parse the response, and extract a list of earthquakes.
        return QueryUtils.fetchNewsData();
    }
}