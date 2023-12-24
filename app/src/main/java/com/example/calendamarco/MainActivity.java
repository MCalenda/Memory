package com.example.calendamarco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    ArrayList<View> allButton;
    ArrayList<String> carte;
    Button firstClick;
    Button secondClick;
    int score;
    TextView scoreTextView;
    TextView scoreLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        score = 0;
        scoreLabel = (TextView) findViewById(R.id.scoreLabel);
        scoreTextView = (TextView) findViewById(R.id.score);
        scoreTextView.setText(String.valueOf(score));

        firstClick = new Button(this);
        firstClick.setTag("");
        secondClick = new Button(this);
        secondClick.setTag("");

        carte = new ArrayList<String>();
        carte.add("\uD83C\uDF49");
        carte.add("\uD83C\uDF49");
        carte.add("\uD83C\uDF4C");
        carte.add("\uD83C\uDF4C");
        carte.add("\uD83C\uDF4E");
        carte.add("\uD83C\uDF4E");
        carte.add("\uD83C\uDF52");
        carte.add("\uD83C\uDF52");
        carte.add("\uD83E\uDD51");
        carte.add("\uD83E\uDD51");
        carte.add("\uD83C\uDF4B");
        carte.add("\uD83C\uDF4B");
        carte.add("\uD83C\uDF4A");
        carte.add("\uD83C\uDF4A");
        carte.add("\uD83C\uDF50");
        carte.add("\uD83C\uDF50");
        Collections.shuffle(carte);

        allButton = new ArrayList<View>();
        allButton = (findViewById(R.id.memoryTable)).getTouchables();
        int index = 0;
        for (View v : allButton) {
            Button b = (Button) v;
            b.setTag(carte.get(index++));
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button b = (Button) view;
                    //Al primo click
                    if (String.valueOf(firstClick.getTag()).matches("")) {
                        firstClick = b;
                        firstClick.setText(String.valueOf(b.getTag()));
                    }
                    //Al secondo click
                    else {
                        secondClick = b;
                        secondClick.setText(String.valueOf(b.getTag()));

                    }
                    //Evitare doppio click sullo stesso bottone
                    if (secondClick == firstClick) {
                        return;
                    }
                    //Logica esito secondo click
                    if (!secondClick.getText().toString().matches("")) {
                        //IF I due bottoni cliccati sono uguali ELSE error
                        if (firstClick.getTag().toString().matches(secondClick.getTag().toString())) {
                            firstClick.setTextColor(getResources().getColor(R.color.correctColor));
                            firstClick.setEnabled(false);
                            secondClick.setTextColor(getResources().getColor(R.color.correctColor));
                            secondClick.setEnabled(false);
                            firstClick = new Button(MainActivity.this);
                            firstClick.setTag("");
                            secondClick = new Button(MainActivity.this);
                            secondClick.setTag("");
                            AggiornaScore();
                            CheckEndGame();
                        } else {
                            for (View v : allButton) {
                                v.setClickable(false);
                                scoreLabel.setTextColor(getResources().getColor(R.color.errorColor));
                                scoreTextView.setTextColor(getResources().getColor(R.color.errorColor));
                            }
                            //Delay di mezzo secondo
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    scoreLabel.setTextColor(getResources().getColor(R.color.standardText));
                                    scoreTextView.setTextColor(getResources().getColor(R.color.standardText));
                                    for (View v : allButton) {
                                        v.setClickable(true);
                                    }
                                    firstClick.setText("");
                                    secondClick.setText("");
                                    firstClick = new Button(MainActivity.this);
                                    firstClick.setTag("");
                                    secondClick = new Button(MainActivity.this);
                                    secondClick.setTag("");
                                    AggiornaScore();
                                }
                            }, 500);
                        }
                    }
                }
            });
        }

    }

    //Aggiorno lo score ed il TextView collegato
    public void AggiornaScore() {
        score++;
        scoreTextView.setText(String.valueOf(score));
    }

    public void CheckEndGame() {
        boolean ended = true;
        Intent intent;
        for (View v : allButton) {
            Button b = (Button) v;
            if (b.getText().toString().matches("")) {
                ended = false;
                break;
            }
        }
        if (ended) {
            intent = new Intent(MainActivity.this, End.class);
            intent.putExtra("score",String.valueOf(score));
            startActivity(intent);
        }
    }
}