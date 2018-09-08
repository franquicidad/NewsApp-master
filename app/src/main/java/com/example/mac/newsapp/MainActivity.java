package com.example.mac.newsapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.app.LoaderManager;

import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mac.newsapp.News;
import com.example.mac.newsapp.NewsAdapter;
import com.example.mac.newsapp.NewsAsyncTaskLoader;
import com.example.mac.newsapp.R;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {

    public static final String LOG_TAG = MainActivity.class.getName();
    private static String BASE_URL =
            "http://content.guardianapis.com/search?page-size=18&api-key=test&show-fields=thumbnail&show-tags=contributor&q=";


    /**
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int NEWS_LOADER_ID = 1;

    private NewsAdapter adapter;

    private ListView listNews;

    private EditText editText;

    private Button search;

    private TextView mEmptyStateTextView;

    private boolean isFirstSearch=true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = (ListView) findViewById(R.id.ListView);
        editText = (EditText) findViewById(R.id.Edit);
        search = (Button) findViewById(R.id.Search);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listNews.setEmptyView(mEmptyStateTextView);

        adapter = new NewsAdapter(getBaseContext(), new ArrayList<News>());



        listNews.setAdapter(adapter);

        //Missing new AsyncTask

        search.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                // Get a reference to the ConnectivityManager to check state of network connectivity
                ConnectivityManager connMgr = (ConnectivityManager)
                        getSystemService(Context.CONNECTIVITY_SERVICE);

                // Get details on the currently active default data network
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

                // If there is a network connection, fetch data
                if (networkInfo != null && networkInfo.isConnected()) {

                    // Get a reference to the LoaderManager, in order to interact with loaders.
                    LoaderManager loaderManager = getSupportLoaderManager();

                    // Initialize the loader. Pass in the int ID constant defined above and pass in null for
                    // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
                    // because this activity implements the LoaderCallbacks interface).
                    if(isFirstSearch){
                        loaderManager.initLoader(NEWS_LOADER_ID, null, MainActivity.this);
                        isFirstSearch=false;
                    }else{
                        loaderManager.restartLoader(NEWS_LOADER_ID, null, MainActivity.this);

                    }
                } else {

                    // Update empty state with no connection error message
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                }

                //Missing new AsyncTask
            }
        });

        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                News newsUrl=(News) adapterView.getItemAtPosition(i);
                String url = newsUrl.getWebUrl();
                Intent web = new Intent(Intent.ACTION_VIEW);
                web.setData(Uri.parse(url));
                try {
                    startActivity(web);
                }catch (ActivityNotFoundException e){
                    Log.e(LOG_TAG,"No Activity");
                }
            }
        });

    }

    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String q = editText.getText().toString();

        return new NewsAsyncTaskLoader(this, BASE_URL +q);
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {

        adapter.clear();
        if (data != null) {
            adapter.addAll(data);
        }

    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {

        adapter.clear();

    }
}

//http://content.guardianapis.com/search?page-size=18&api-key=test

//http://content.guardianapis.com/search?section=world&page-size=18&api-key=test
