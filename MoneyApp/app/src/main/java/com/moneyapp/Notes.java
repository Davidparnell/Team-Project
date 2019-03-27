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
import android.widget.Toast;
import android.app.Activity;

public class Notes extends Fragment implements View.OnClickListener{

    public static final String TAG = "Notes";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_notes, container, false);

        ImageButton five = view.findViewById(R.id.euro5);
        five.setOnClickListener(this);

        ImageButton ten = view.findViewById(R.id.euro10);
        ten.setOnClickListener(this);

        ImageButton twenty = view.findViewById(R.id.euro20);
        twenty.setOnClickListener(this);

        ImageButton fifty = view.findViewById(R.id.euro50);
        fifty.setOnClickListener(this);

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

            //€50 pressed
            case R.id.euro50:
                Toast.makeText(getActivity().getApplicationContext(),"€50 Added",Toast.LENGTH_SHORT).show();
                break;

            //€20 pressed
            case R.id.euro20:
                Toast.makeText(getActivity().getApplicationContext(),"€20 Added",Toast.LENGTH_SHORT).show();
                break;

            //€10 pressed
            case R.id.euro10:
                Toast.makeText(getActivity().getApplicationContext(),"€10 Added",Toast.LENGTH_SHORT).show();
                break;

            //€5 pressed
            case R.id.euro5:
                Toast.makeText(getActivity().getApplicationContext(),"€5 Added",Toast.LENGTH_SHORT).show();
                break;

            //Wallet pressed
            case R.id.wallet:
                Toast.makeText(getActivity().getApplicationContext(),"Open Wallet",Toast.LENGTH_SHORT).show();
                break;
        }

    }
}
