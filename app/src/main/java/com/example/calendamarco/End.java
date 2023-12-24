package com.example.calendamarco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class End extends AppCompatActivity {

    TextView scoreTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);

        scoreTextView = (TextView) findViewById(R.id.score);
        Intent intent = getIntent();
        String score = intent.getStringExtra("score");
        scoreTextView.setText(score);
    }
}