package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class camera extends AppCompatActivity implements View.OnClickListener {

    ImageButton camera;
    TextView placeholder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        camera = findViewById(R.id.camera);
        camera.setOnClickListener(this);

        placeholder = findViewById(R.id.holdertext);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        //Place Holder for taken camera
        if (v == (View) camera)
        {
            Toast.makeText(getApplicationContext(), "--- Photo Taken ---",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
