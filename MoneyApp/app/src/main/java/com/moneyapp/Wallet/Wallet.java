package com.moneyapp.Wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;
import com.moneyapp.MainActivity;
import com.moneyapp.R;


public class Wallet extends AppCompatActivity implements View.OnClickListener {

    //Initialize Layout Buttons
    ImageButton five, ten, twenty, fifty, wallet, coins;

    //Initialize DB
    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);

        //€5 Button
        five = findViewById(R.id.euro5);
        five.setOnClickListener(this);

        //€10 button
        ten = findViewById(R.id.euro10);
        ten.setOnClickListener(this);

        //€20 button
        twenty = findViewById(R.id.euro20);
        twenty.setOnClickListener(this);

        //€50 button
        fifty = findViewById(R.id.euro50);
        fifty.setOnClickListener(this);

        //Wallet button
        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

        //Button to change to coins
        coins = findViewById(R.id.CoinBtn);
        coins.setOnClickListener(this);

        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v) {
        //Money Buttons
        if (v == (View) five) {
            //Add 5 to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- 5 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setNote5(walletData.getNote5()+1);
        } else if (v == (View) ten) {
            //Add 10 to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- 10 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setNote10(walletData.getNote10()+1);
        } else if (v == (View) twenty) {
            //Add 20 to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- 20 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setNote20(walletData.getNote20()+1);
        } else if (v == (View) fifty) {
            //Add 50 to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- 50 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setNote50(walletData.getNote50()+1);
            //Options
        } else if (v == (View) wallet) {
            //Open EditWallet Activity
            Intent intent = new Intent(getApplicationContext(), EditWallet.class);
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            startActivity(intent);
        } else if (v == (View) coins) {
            //Change to coins activity
            Intent intent = new Intent(getApplicationContext(), WalletCoins.class);
            intent.putExtra("notes", walletData.getNotes());
            startActivity(intent);
        }
    }

    WalletData getWalletData(){
        return walletData;
    }
}