package com.moneyapp.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.moneyapp.MainActivity;
import com.moneyapp.R;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class EditWallet extends AppCompatActivity  implements View.OnClickListener{

    ImageButton confirm, cancel;

    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_wallet);

        //Wallet selection is ok
        confirm = findViewById(R.id.ConfirmWallet);
        confirm.setOnClickListener(this);

        //Wallet selection is incorrect, stuff needs to be added
        cancel = findViewById(R.id.CancelWallet);
        cancel.setOnClickListener(this);

        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();
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
        walletData.setNotes(intent.getIntArrayExtra("notes"));
        walletData.setCoins(intent.getIntArrayExtra("coins"));

        Log.d("WALLET", "Edit");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();

        if(intent.getStringExtra("type").equals("wallet")) {
            intent.setClass(getApplicationContext(), Wallet.class);
        } else {
            intent.setClass(getApplicationContext(), WalletCoins.class);
        }

        intent.putExtra("notes", walletData.getNotes());
        intent.putExtra("coins", walletData.getCoins());
        intent.putExtra("type", "editWallet");
        startActivity(intent);
        Log.d("WALLET", "Notes");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }*/

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        //If confirm button pressed
        if(v == (View) confirm)
        {
            databaseInsert();
            //Return to main
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            databaseInsert();
            startActivity(intent);
            finish();
        }

        //if cancel button pressed
        else if(v == (View) cancel)
        {
            //Return to wallet
            onBackPressed();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    public void databaseInsert(){
        Date date = Calendar.getInstance().getTime();
        int notes[] = walletData.getNotes();
        int coins[] = walletData.getCoins();
        float balance = notes[0] * 50f + notes[1] * 20f + notes[2] * 10f + notes[3] * 5f;
        balance += coins[0] * 2.00f + coins[1] * 1.00f + coins[2] * 0.50f + coins[3] * 0.20f + coins[4] * 0.10f + coins[5] * 0.05f;

        walletData.setWalletOptions(date, balance, 0);
        walletDAO.insert(walletData);
    }
}
