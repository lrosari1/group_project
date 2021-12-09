package com.example.project3;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchGame extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mGameTitle;
    private WeakReference<TextView> mDescription;

    FetchGame(TextView game, TextView description) {
        this.mGameTitle = new WeakReference<>(game);
        this.mDescription = new WeakReference<>(description);
    }

    protected String getAPI(String query) throws IOException {
        //freetogame API
        String apiURL = "https://www.freetogame.com/api/games";

        apiURL += query;

        //Make connection to API
        URL requestURL = new URL(apiURL);
        HttpURLConnection urlConnection = (HttpURLConnection) requestURL.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.connect();

        //Receive the response
        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        //Create a String with the reponse
        StringBuilder builder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
            builder.append("\n");
        }
        String jsonString = builder.toString();
        Log.d("FetchGameJsonString", jsonString);
        return jsonString;
    }

    protected String doInBackground(String... strings) {
        String jsonString = null;
        try{
            jsonString = getAPI();
        } catch (IOException e){
            e.printStackTrace();
        }
        return jsonString;
    }

    protected  void onPostExecute(String s){
        super.onPostExecute(s);
        String title = null;
        String description = null;
        JSONObject jsonObject = null;
        JSONArray itemsArray = null;
        int i = 0;

        try {
            jsonObject = new JSONObject(s);
            itemsArray = jsonObject.getJSONArray("items");
            while(i<itemsArray.length() && title==null & description==null) {
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");
                title = volumeInfo.getString("title");
                description = volumeInfo.getString("short_description");
                mDescription.get().setText(description);
                mGameTitle.get().setText(title);
                i++;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
