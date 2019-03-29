package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Transaction.Camera;
import com.moneyapp.Transaction.PaySuggestion;
import com.moneyapp.Wallet.Wallet;
import android.util.Log;

import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    ImageButton BtnHist, BtnWallet,BtnCamera;
    ImageView BtnCamT2S, BtnWallT2S, BtnBalT2S;
    TextView Balance, CurrentBal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database object
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "wallet")
                .allowMainThreadQueries()
                .build();

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

        WalletDAO walletDAO = database.getWalletDAO();
        //Date formatting
        Date date = Calendar.getInstance().getTime();;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        WalletData walletData = new WalletData();
        walletData.setWalletOptions(strDate, (float) 10.00, 10,"location1", "receipt1");
        walletData.setNotes(0, 0, 1, 0);
        walletData.setCoins(1,0,0,0,0,0);
        walletDAO.insert(walletData);
        WalletData wallet = walletDAO.getRecentWallet();
        Balance.setText(String.valueOf(wallet.getBalance()));
        Balance.setTextSize(80);

        Log.d("WAL", walletDAO.getRecentWallet().toString());
        Log.d("WAL", walletDAO.getWalletHistory().toString());
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        if(v == (View) BtnHist)
        {
            Intent intent = new Intent(getApplicationContext(), History.class);
            startActivity(intent);
        }

        else if (v == (View) BtnWallet)
        {
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            startActivity(intent);
        }

        else if(v == (View) BtnCamera)
        {
            //Intent intent = new Intent(getApplicationContext(), Camera.class);
            //startActivity(intent);
            Intent intent = new Intent(getApplicationContext(), Camera.class);
            //intent.putExtra("register", "53.50");
            startActivity(intent);
        }

        else if(v == (View) BtnWallT2S)
        {
            String walletinfo = "wallet";
            //Text to speech for wallet, Toast for now
            Toast.makeText(getApplicationContext(),"--- Wallet ---",
                    Toast.LENGTH_SHORT).show();
            //Intent for text to speech.
            Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
            //Pass data to be spoken to the SpeechService class.
            speechIntent.putExtra("textData", walletinfo);
            //Start Text to speech.
            getApplicationContext().startService(speechIntent);
        }

        else if(v == (View) BtnBalT2S)
        {
            //Text to speech for Balance, Toast for now
            Toast.makeText(getApplicationContext(),"--- €XX.XX ---",
                    Toast.LENGTH_SHORT).show();
            //Intent for text to speech.
            Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
            //Pass data to be spoken to the SpeechService class.
            speechIntent.putExtra("textData", Balance.getText());
            //Start Text to speech.
            getApplicationContext().startService(speechIntent);
        }

        else if(v == (View) BtnCamT2S)
        {
            //Text to speech for camera, Toast for now
            Toast.makeText(getApplicationContext(),"--- Camera ---",
                    Toast.LENGTH_SHORT).show();
        }
    }
}