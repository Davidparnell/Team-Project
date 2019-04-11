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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {
    ImageButton btnHist, btnWallet, btnCamera;
    ImageView btnBalT2S;
    TextView balanceView;
    String balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database object
        AppDatabase database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "wallet")
                //.fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();

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
        //balanceView.setOnLongClickListener(this);

        //Display balanceView
        WalletDAO walletDAO = database.getWalletDAO();
        walletDAO.deleteAll();
        //Date formatting
        Date date = Calendar.getInstance().getTime();;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK);
        String strDate = dateFormat.format(date);

        //dummy data
        WalletData walletData = new WalletData();
        //dateFormat.parse("2019-10-02 15:15:14" for testing date with hard coding
        walletData.setWalletOptions(date, (float) 73.50, (float)10.00);
        walletData.setNotes(new int[] {1, 1, 0, 0});
        walletData.setCoins(new int[] {1,1,1,0,0,0});
        walletDAO.insert(walletData);

        //List<WalletData> walletHist = walletDAO.getWalletHistory();
        //Log.d("DATE", walletHist.toString());
        //main balanceView
        WalletData wallet = walletDAO.getRecentWallet();
        balance = String.format(Locale.UK, "%.02f", wallet.getBalance());
        balanceView.setText("\u20ac "+balance);     //text view with balance and euro sign
        balanceView.setTextSize(80);
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
                String buttoninfo = "wallet";
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", buttoninfo);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
            case R.id.Camera:
            {
                String buttoninfo = "camera";
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", buttoninfo);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
            case R.id.History:
            {
                String buttoninfo = "history";
                //Intent for text to speech.
                Intent speechIntent = new Intent(getApplicationContext(), SpeechService.class);
                //Pass data to be spoken to the SpeechService class.
                speechIntent.putExtra("textData", buttoninfo);
                //Start Text to speech.
                getApplicationContext().startService(speechIntent);
                break;
            }
        }

        return true;
    }
}