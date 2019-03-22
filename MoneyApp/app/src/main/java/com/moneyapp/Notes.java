package com.moneyapp;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class Notes extends Fragment implements View.OnClickListener{

    ImageButton five, ten, twenty, fifty, confirm, wallet;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);

        five = findViewById(R.id.euro5);
        five.setOnClickListener(this);

        ten = findViewById(R.id.euro10);
        ten.setOnClickListener(this);

        twenty = findViewById(R.id.euro20);
        twenty.setOnClickListener(this);

        fifty = findViewById(R.id.euro50);
        fifty.setOnClickListener(this);

        confirm = findViewById(R.id.Confirm);
        confirm.setOnClickListener(this);

        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

    }
}
