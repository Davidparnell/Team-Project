package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.content.Intent;
import android.widget.TextView;

import com.moneyapp.database.AppDatabase;
import com.moneyapp.history.History;
import com.moneyapp.transaction.Camera;
import com.moneyapp.wallet.Wallet;

import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;

import java.util.Calendar;
import java.util.Locale;

/*
Displays the balance and a text-to-speech button for it.
Gives options to go to the wallet, camera and history
With text-to-speech on all button if longClicked
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    ImageButton btnHist, btnWallet, btnCamera;
    ImageView btnBalT2S;
    TextView balanceView;
    String balance;

    AppDatabase database;
    WalletDAO walletDAO;
    WalletData walletData = new WalletData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database object
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wallet")
                .allowMainThreadQueries()
                .build();

        walletDAO = database.getWalletDAO();

        //Button for History
        btnHist = findViewById(R.id.History);
        btnHist.setOnClickListener(this);
        btnHist.setOnLongClickListener(this);

        //Button for Wallet
        btnWallet = findViewById(R.id.Wallet);
        btnWallet.setOnClickListener(this);
        btnWallet.setOnLongClickListener(this);

        //Button for Camera so bill can be read in
        btnCamera = findViewById(R.id.Camera);
        btnCamera.setOnClickListener(this);
        btnCamera.setOnLongClickListener(this);

        balanceView = findViewById(R.id.Balance);
        btnBalT2S = findViewById(R.id.BalanceT2S);
        btnBalT2S.setOnClickListener(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if(walletDAO.getRecentWallet() != null){
            walletData = walletDAO.getRecentWallet();
        }else{
            walletData.setWalletOptions(Calendar.getInstance().getTime(), 0, 0);
            walletData.setNotes(new int[] {0, 0, 0, 0});
            walletData.setCoins(new int[] {0,0,0,0,0,0});
            walletDAO.insert(walletData);
        }

        balance = String.format(Locale.UK, "%.02f", walletData.getBalance());
        balanceView.setText("\u20ac" +balance);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        //History Button pressed
        if(v == (View) btnHist)
        {
            Intent intent = new Intent(getApplicationContext(), History.class);
            startActivity(intent);
        }

        //Wallet button pressed
        else if (v == (View) btnWallet)
        {
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            startActivity(intent);
        }

        //Camera Button Pressed
        else if(v == (View) btnCamera)
        {
            Intent intent = new Intent(getApplicationContext(), Camera.class);
            startActivity(intent);
        }
        else if(v ==(View) btnBalT2S)
        {
            //Intent for text to speech.
            Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
            //Pass data to be spoken to the SpeechService class.
            speechIntent.putExtra("textData", balance);
            //Start Text to speech.
            getApplicationContext().startService(speechIntent);
        }
    }

    @Override
    public boolean onLongClick(View v)
    {
        switch (v.getId())
        {
            case R.id.Balance:
            {
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", balance);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
            case R.id.Wallet:
            {
                String buttonInfo = "wallet";
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", buttonInfo);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
            case R.id.Camera:
            {
                String buttonInfo = "camera";
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", buttonInfo);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
            case R.id.History:
            {
                String buttonInfo = "history";
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", buttonInfo);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
        }
        return true;
    }
}