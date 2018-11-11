package com.example.android.abndp7;

import android.text.TextUtils;
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


public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    private static final String RESPONSE = "response";
    private static final String RESULTS = "results";
    private static final String WEBTITLE = "webTitle";
    private static final String SECTIONNAME = "sectionName";
    private static final String WEBPUBLICATIONDATE = "webPublicationDate";
    private static final String WEBURL ="webUrl";

    private QueryUtils() {
    }


    public static List<News> fetchFeeedNewsData(String requestUrl) {


        URL url = createUrl(requestUrl);


        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }


        List<News> aNews = extractFeatureFromJson(jsonResponse);


        return aNews;
    }


    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";


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


            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {

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




    private static List<News> extractFeatureFromJson(String feedNewsJSON) {

        if (TextUtils.isEmpty(feedNewsJSON)) {
            return null;
        }


        List<News> aNews = new ArrayList<>();

        try {


            JSONObject baseJsonResponse = new JSONObject(feedNewsJSON);

            JSONObject baseJsonResults = baseJsonResponse.getJSONObject(RESPONSE);


            JSONArray feedNewsArray = baseJsonResults.getJSONArray(RESULTS);

            for (int i = 0; i < feedNewsArray.length(); i++) {
                JSONObject currentFeedNews = feedNewsArray.getJSONObject(i);
                String title = currentFeedNews.getString(WEBTITLE);
                String author = "(unknown author)";
                if (currentFeedNews.has("fields")) {
                    JSONObject fieldsObject = currentFeedNews.getJSONObject("fields");

                    if (fieldsObject.has("byline")) {
                        author = fieldsObject.getString("byline");
                    }
                }
                String sectionName = currentFeedNews.getString(SECTIONNAME);
                String date = currentFeedNews.getString(WEBPUBLICATIONDATE);
                String url = currentFeedNews.getString(WEBURL);
                News feed = new News(title, sectionName, author, date, url);
                aNews.add(feed);
            }

        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing JSON results", e);
        }


        return aNews;
    }
}