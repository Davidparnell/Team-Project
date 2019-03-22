package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Fragment;

public class Wallet extends AppCompatActivity implements View.OnClickListener {

    ImageButton Coins, Notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);

        Notes = findViewById(R.id.NoteButton);
        Notes.setOnClickListener(this);

        Coins = findViewById(R.id.CoinButton);
        Coins.setOnClickListener(this);

    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v) {

        if(v == (View) Notes)
        {
            ChangeFragment(v);
        }

        else if(v == (View) Coins)
        {
            ChangeFragment(v);
        }

    }

    public  void ChangeFragment(View view)
    {
        Fragment fragment;

    }
}