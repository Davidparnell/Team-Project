package com.moneyapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;


public class Coins extends Fragment implements View.OnClickListener {

    public static final String TAG = "Coins";

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

        ImageButton five = view.findViewById(R.id.five_cent);
        five.setOnClickListener(this);

        ImageButton confirm = view.findViewById(R.id.Confirm);
        confirm.setOnClickListener(this);

        ImageButton wallet = view.findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            //€2 Pressed
            case R.id.two_euro:
                Toast.makeText(getActivity().getApplicationContext(),"€2 Added",Toast.LENGTH_SHORT).show();
                break;

            //€1 pressed
            case R.id.one_euro:
                Toast.makeText(getActivity().getApplicationContext(),"€1 Added",Toast.LENGTH_SHORT).show();
                break;

            //50c pressed
            case R.id.fifty_cent:
                Toast.makeText(getActivity().getApplicationContext(),"50 cent Added",Toast.LENGTH_SHORT).show();
                break;

            //20c pressed
            case R.id.twenty_cent:
                Toast.makeText(getActivity().getApplicationContext(),"20 cent Added",Toast.LENGTH_SHORT).show();
                break;

            //10c pressed
            case R.id.ten_cent:
                Toast.makeText(getActivity().getApplicationContext(),"10 cent Added",Toast.LENGTH_SHORT).show();
                break;

            //5c  pressed
            case R.id.five_cent:
                Toast.makeText(getActivity().getApplicationContext(),"5 cent Added",Toast.LENGTH_SHORT).show();
                break;

            //Wallet pressed
            case R.id.wallet:
                Toast.makeText(getActivity().getApplicationContext(),"Wallet pressed",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}