package com.example.project3;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;


public class FetchGame extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mGameTitle;
    private WeakReference<TextView> mDescription;



    FetchGame(TextView game, TextView description) {
        this.mGameTitle = new WeakReference<>(game);
        this.mDescription = new WeakReference<>(description);
    }

    protected String getAPI() throws IOException {
        //freetogame API
        //hardcoded url, need multiple activities or query
        String apiURL = "https://www.freetogame.com/api/games?category=shooter";
        //apiURL += ;
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

    Random r = new Random();

    protected  void onPostExecute(String s){
        super.onPostExecute(s);
        String title = null;
        String description = null;
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        int i = 0;


        try {

            jsonArray = new JSONArray(s);
            int j = r.nextInt(jsonArray.length()-1);
            JSONObject choice = jsonArray.getJSONObject(j);

            //itemsArray = jsonObject.getJSONArray(String.valueOf(j));
           // while(i<itemsArray.length() && title==null & description==null) {
            title = choice.getString("title");
            description = choice.getString("short_description");
            mDescription.get().setText(description);
            mGameTitle.get().setText(title);
             //   i++;
             //   }


    } catch (Exception e){
            e.printStackTrace();
        }
    }

}
