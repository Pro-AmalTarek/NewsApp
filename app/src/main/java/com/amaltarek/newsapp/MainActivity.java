package com.amaltarek.newsapp;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.amaltarek.newsapp.Adapter.NewsAdapter;
import com.amaltarek.newsapp.Loader.NewsLoader;
import com.amaltarek.newsapp.Model.News;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>>, SwipeRefreshLayout.OnRefreshListener{

    /** News Adapter */
    private NewsAdapter mAdapter;

    /** News Loader Constant ID */
    private static int NEWS_LOADER_ID = 0;

    /** Swipe Refresh Layout */
    SwipeRefreshLayout mSwipeLayout;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    /** Check if Loader init or restart with swipe layout */
    private boolean mLoaderInit = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Swipe Refresh Layout from Layout
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
        mSwipeLayout.setOnRefreshListener(this);
        mSwipeLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));

        // Get List View and Make it Scroll smoothly
        final ListView listView = (ListView) findViewById(R.id.news_list);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getChildAt(0) != null) {
                    mSwipeLayout.setEnabled(listView.getFirstVisiblePosition() == 0 && listView.getChildAt(0).getTop() == 0);
                }
            }
        });

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new NewsAdapter(this, new ArrayList<News>());
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News currentNews = mAdapter.getItem(i);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri newsUri = Uri.parse(currentNews.getNewsUrl());
                Intent intent = new Intent(Intent.ACTION_VIEW, newsUri);
                startActivity(intent);
            }
        });

        checkConnectivity();
    }

    /**
     * Check Network Connectivity to fetch new Data
     */
    private void checkConnectivity(){
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();

            // Check if Loader this is the first Time to init or not just restart
            if(mLoaderInit){
                // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
                mLoaderInit = false;
            }else{
                loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
            }

        } else {
            // Otherwise, display error
            // First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);

            // Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        return new NewsLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> news) {
        // Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.no_content_found);

        /*Before or at the same time we get this message of no news (or in the current case before seeing a non-working list again), we should check connectivity again!

        To make this work correctly all the time (it works fine if there is really no news (again, assuming you clear the adapter, see next note), but not if the problem is no internet, see the scenario described in Project Review) you have to add another connectivity check.

        Can you make this work in your code? We cannot just give sample code but think if/else for example, because there are many ways to get this done, the key is (1) understanding what is currently happening, and this means testing out the error messages; (2) understanding how it actually should work; (3) designing the code.

        Note: simulate loss of connection by putting device in airplane mode.

        Note: simulate no news by modifying the query, you could search for lkjlkjlkjlkjlkjlkjjkl or something sure to have zero results!

        Have fun here, because once you fix this you will have mastered a very special detail that many overlook and gloss over! And those little details make the difference between good and great in app dev!*/
        
        // Clear the adapter of previous earthquake data
        //You have this commented out, but clearing the adapter is important to prevent a memory leak. This code should activate before the error message in line 138.
        //mAdapter.clear();
        mSwipeLayout.setRefreshing(false);
        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (news != null && !news.isEmpty()) {
            mAdapter.setNotifyOnChange(false);
            mAdapter.clear();
            mAdapter.setNotifyOnChange(true);
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }

    /**
     * To refresh Loader and Update news ITem
     */
    @Override
    public void onRefresh() {
        checkConnectivity();
    }
}
