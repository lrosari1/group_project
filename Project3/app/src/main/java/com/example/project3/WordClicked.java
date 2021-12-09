package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WordClicked extends AppCompatActivity {

    private TextView mGameTitle;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_clicked);

        mGameTitle = (TextView) findViewById(R.id.gameTitle);
        mDescription = (TextView) findViewById(R.id.gameDescription);

        FetchGame fb = new FetchGame(mGameTitle, mDescription);
        fb.execute();


    }

}

