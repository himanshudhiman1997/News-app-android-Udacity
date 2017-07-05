package com.example.android.newsapp;

import android.text.TextUtils;

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

/**
 * Created by mr on 18-03-2017.
 */

public class NewsUtils {
    private NewsUtils(){}
    public static List<News>  fetchNewsData(String requestUrl)
    {
        URL url = createUrl(requestUrl);
        String jsonResp=null;
        try
        {
            jsonResp = makeHttpRequest(url);
        }
        catch (IOException e)
        {

        }
        List<News> news = extractFeatureFromJson(jsonResp);
        return  news;
    }
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
        }
        return url;
    }
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = null;
        if (url==null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(1500);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
        }
        finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private static String readFromStream(InputStream inStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        if (inStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();
    }

    private static List<News> extractFeatureFromJson(String newsJSON) {
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(newsJSON);
            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
            JSONArray jsonArray = jsonObject1.getJSONArray("results");
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject2 = jsonArray.optJSONObject(i);
                String section_name = jsonObject2.optString("sectionName");
                String web_title = jsonObject2.optString("webTitle");
                String url = jsonObject2.optString("webUrl");
                newsList.add(new News(section_name,web_title,url));
            }
        } catch (JSONException e) {
        }
        return newsList;
    }
}
