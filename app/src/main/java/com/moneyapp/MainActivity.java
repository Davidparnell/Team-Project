package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView t2sbutton = findViewById(R.id.BalanceT2S);

        t2sbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        Intent tts_intent = new Intent(getApplicationContext(), Text2SpeechTest.class);
        startActivity(tts_intent);
    }
}
