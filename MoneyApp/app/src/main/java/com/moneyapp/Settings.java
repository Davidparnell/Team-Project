package com.moneyapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    TextView CBText, DDText;
    Switch CBSwitch, DDSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        CBText = findViewById(R.id.ColorBlindTxt);

        CBSwitch = findViewById(R.id.ColourBlindSwitch);
        CBSwitch.setOnClickListener(this);

        DDText = findViewById(R.id.DragDropTxt);

        DDSwitch = findViewById(R.id.DragDropSwtch);
        DDSwitch.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == (View) CBSwitch)
        {
            CBSwitch.setChecked(true);
            //Change Colours
        }

        else if(v == (View) DDSwitch)
        {
            DDSwitch.setChecked(true);
            //Drag Drop mode
        }
    }
}
