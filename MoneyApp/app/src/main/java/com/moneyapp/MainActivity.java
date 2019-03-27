package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton BtnHist, BtnWallet,BtnCamera;
    ImageView BtnCamT2S, BtnWallT2S, BtnBalT2S;
    TextView Balance, CurrentBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnHist = findViewById(R.id.History);
        BtnHist.setOnClickListener(this);

        BtnWallet = findViewById(R.id.Wallet);
        BtnWallet.setOnClickListener(this);

        BtnCamera = findViewById(R.id.Camera);
        BtnCamera.setOnClickListener(this);

        BtnWallT2S = findViewById(R.id.WalletT2S);
        BtnWallT2S.setOnClickListener(this);

        BtnCamT2S = findViewById(R.id.CameraT2S);
        BtnCamT2S.setOnClickListener(this);

        BtnBalT2S = findViewById(R.id.BalanceT2S);
        BtnBalT2S.setOnClickListener(this);

        Balance = findViewById(R.id.Balance);
        CurrentBal = findViewById(R.id.cbalanceText);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {

        if(v == (View) BtnHist)
        {
            Intent intent = new Intent(getApplicationContext(), history.class);
            startActivity(intent);
        }

        else if (v == (View) BtnWallet)
        {
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            startActivity(intent);
        }

        else if(v == (View) BtnCamera)
        {
            Intent intent = new Intent(getApplicationContext(), camera.class);
            startActivity(intent);
        }

        else if(v == (View) BtnWallT2S)
        {
            //Text to speech for wallet, Toast for now
            Toast.makeText(getApplicationContext(),"--- Wallet ---",
                    Toast.LENGTH_SHORT).show();
        }

        else if(v == (View) BtnBalT2S)
        {
            //Text to speech for Balance, Toast for now
            Toast.makeText(getApplicationContext(),"--- €XX.XX ---",
                    Toast.LENGTH_SHORT).show();
        }

        else if(v == (View) BtnCamT2S)
        {
            //Text to speech for camera, Toast for now
            Toast.makeText(getApplicationContext(),"--- Camera ---",
                    Toast.LENGTH_SHORT).show();
        }
    }
}
