package com.example.mac.newsapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static com.example.mac.newsapp.MainActivity.LOG_TAG;


/**
 * Created by mac on 24/08/17.
 */

public class QueryUtils {


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Returns new URL object from the given string URL.
     */

    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("LOG_TAG", "Problem building the url", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<News> extractNewsItems(String jsonResponse) {
        String SectionName = "";
        String Title="";
        String firstNameAuthor = "";
        String secondNameAuthor="";
        String publishedDate = "";
        String imageLink = "";
        String webUrl="";

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<News> books = new ArrayList<>();
        try {
            JSONObject baseJsonResponce = new JSONObject(jsonResponse);
            JSONObject newsObject = baseJsonResponce.getJSONObject("response");
            JSONArray results = newsObject.getJSONArray("results");

            for (int i = 0; i < newsObject.length(); i++) {

                JSONObject currentnews = results.getJSONObject(i);

                if (currentnews.has("sectionId")) {
                    SectionName = currentnews.getString("sectionId");
                }
                if(currentnews.has("webTitle")){
                    Title=currentnews.getString("webTitle");
                }
                if (currentnews.has("tags")) {
                    JSONArray tag= currentnews.getJSONArray("tags");
                    if(tag.length() > 0) {
                        JSONObject firstname = tag.getJSONObject(0);
                        firstNameAuthor = firstname.getString("id");
                    }
                }
                if (currentnews.has("webPublicationDate"))
                    publishedDate = currentnews.getString("webPublicationDate");

                if (currentnews.has("fields")) {
                    JSONObject ilink = currentnews.getJSONObject("fields");
                    imageLink = ilink.getString("thumbnail");
                }
                if(currentnews.has("webUrl")){
                     webUrl=currentnews.getString("webUrl");

                }

                News news = new News(SectionName,Title,firstNameAuthor,secondNameAuthor,publishedDate,imageLink,webUrl);
                books.add(news);


            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the news JSON results", e);
        }
        return books;
    }

    public static List<News> fetchNewsList(String urlString) {
        URL url = createUrl(urlString);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extractNewsItems(jsonResponse);
    }
}

