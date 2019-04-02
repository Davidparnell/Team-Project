package com.moneyapp.Wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;
import com.moneyapp.R;

import androidx.appcompat.app.AppCompatActivity;

public class WalletCoins extends AppCompatActivity implements View.OnClickListener {

    ImageButton two_euro, one_euro, fifty_cent, twenty_cent, ten_cent, five_cent, confirm, wallet, notes;

    AppDatabase database;
    WalletDAO walletDAO;
    WalletData walletData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coins);

        two_euro = findViewById(R.id.two_euro);
        two_euro.setOnClickListener(this);

        one_euro = findViewById(R.id.one_euro);
        one_euro.setOnClickListener(this);

        fifty_cent = findViewById(R.id.fifty_cent);
        fifty_cent.setOnClickListener(this);

        twenty_cent = findViewById(R.id.twenty_cent);
        twenty_cent.setOnClickListener(this);

        ten_cent = findViewById(R.id.ten_cent);
        ten_cent.setOnClickListener(this);

        five_cent =  findViewById(R.id.five_cent);
        five_cent.setOnClickListener(this);

        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

        notes = findViewById(R.id.NoteBtn);
        notes.setOnClickListener(this);

        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();
    }

    @Override
    public void onClick(View v) {

        //Money Buttons
        if (v == (View) two_euro) {
            //Add €1 to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- €2 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin2e(walletData.getCoin2e()+1);
        } else if (v == (View) one_euro) {
            //Add €2 to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- €1 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin2e(walletData.getCoin1e()+1);
        } else if (v == (View) fifty_cent) {
            //Add 50c to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 50c added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin50c(walletData.getCoin50c()+1);
        } else if (v == (View) twenty_cent) {
            //Add 20c to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 20c added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin20c(walletData.getCoin20c()+1);
        } else if(v == (View) ten_cent){
            //Add 10c to wallet, for now toast
            Toast.makeText(getApplicationContext(),"--- 10c added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin10c(walletData.getCoin10c()+1);
        } else if(v == (View) five_cent){
            //Add 5c to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 5c added---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin5c(walletData.getCoin5c()+1);
            //Options
        } else if (v == (View) wallet) {
            //Open EditWallet Activity
            Intent intent = new Intent(getApplicationContext(), EditWallet.class);
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            startActivity(intent);
        } else if (v == (View) notes) {
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            intent.putExtra("coins", walletData.getCoins());
            startActivity(intent);
        }
    }
}
