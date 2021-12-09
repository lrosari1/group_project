package com.example.project3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class WordClicked extends AppCompatActivity {

    private TextView mGameTitle;
    private TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_clicked);

        mGameTitle = (TextView) findViewById(R.id.gameTitle);
        mDescription = (TextView) findViewById(R.id.gameDescription);
    }
}