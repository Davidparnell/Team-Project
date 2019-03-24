package com.moneyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class Coins extends Fragment implements View.OnClickListener {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notes, container, false);

        ImageButton two = view.findViewById(R.id.two_euro);
        two.setOnClickListener(this);

        ImageButton one = view.findViewById(R.id.one_euro);
        one.setOnClickListener(this);

        ImageButton fifty = view.findViewById(R.id.fifty_cent);
        fifty.setOnClickListener(this);

        ImageButton twenty = view.findViewById(R.id.twenty_cent);
        twenty.setOnClickListener(this);

        ImageButton ten = view.findViewById(R.id.ten_cent);
        ten.setOnClickListener(this);

        ImageButton confirm = view.findViewById(R.id.Confirm);
        confirm.setOnClickListener(this);

        ImageButton wallet = view.findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        
    }
}