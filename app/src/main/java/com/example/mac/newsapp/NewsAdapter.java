package com.example.mac.newsapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.example.mac.newsapp.R.id.text;

/**
 * Created by mac on 23/08/17.
 */

public class NewsAdapter extends ArrayAdapter<News> {


    private ImageView newsImage;
    private TextView Section;
    private TextView Title;
    private TextView author;
    private TextView publishedDate;

    private static final String TAG = "NewsAdapter";


    public NewsAdapter(Context context, List<News> objects) {
        super(context, 0, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(getContext()).inflate(R.layout.news_design, parent, false);
        }

        News newsPosition = getItem(position);

        newsImage = (ImageView) v.findViewById(R.id.news_image);
        Log.e(TAG, "Identificador "+newsPosition.getWebUrl());

        if (!TextUtils.isEmpty(newsPosition.getImageLink())) {
            Picasso.with(getContext()).load(newsPosition.getImageLink()).into(newsImage);

        }

        Section = (TextView) v.findViewById(R.id.section);
        Section.setText("Section:" + newsPosition.getSectionName());

        Title = (TextView) v.findViewById(R.id.Title);
        Title.setText("Title:" + newsPosition.getTitle());

        author = (TextView) v.findViewById(R.id.author);

        String cadena = (newsPosition.getFirstNameAuthor() + " " +newsPosition.getSecondNameAuthor());
        Log.i(TAG,"This will be the author"+cadena);
        author.setText("Author:" + cadena);
        //author.setText("Author:" + newsPosition.getFirstNameAuthor() + newsPosition.getSecondNameAuthor());

        // Create a new Date object from the time in milliseconds of the earthquake
//        Date dateObject = new Date(newsPosition.getPublishedDate());
        publishedDate = (TextView) v.findViewById(R.id.published_date);
        // Format the date string (i.e. "Mar 3, 1984")
//        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        publishedDate.setText("Date:"+newsPosition.getPublishedDate());

        return v;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

}

