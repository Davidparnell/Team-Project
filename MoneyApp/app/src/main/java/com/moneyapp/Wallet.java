package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.app.Fragment;
import android.app.FragmentManager;

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

        //If note button pressed change to Note fragment
        if(view == findViewById(R.id.NoteButton))
        {
            fragment = new Notes();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.MoneyMenu,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

        //If coin button pressed change to Coin fragment
        else if(view == findViewById(R.id.CoinButton))
        {
            fragment = new Coins();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.MoneyMenu,fragment);
            ft.addToBackStack(null);
            ft.commit();
        }

    }
}