package com.moneyapp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;

import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;

public class WalletCoins extends AppCompatActivity implements View.OnClickListener {

    //Initialize Layout Buttons
    ImageButton two_euro, one_euro, fifty_cent, twenty_cent,
            ten_cent, five_cent, wallet, notes;

    //Initialize DB objects
    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coins);

        //€2 button
        two_euro = findViewById(R.id.two_euro);
        two_euro.setOnClickListener(this);

        //€1 button
        one_euro = findViewById(R.id.one_euro);
        one_euro.setOnClickListener(this);

        //50c button
        fifty_cent = findViewById(R.id.fifty_cent);
        fifty_cent.setOnClickListener(this);

        //20c button
        twenty_cent = findViewById(R.id.twenty_cent);
        twenty_cent.setOnClickListener(this);

        //10c button
        ten_cent = findViewById(R.id.ten_cent);
        ten_cent.setOnClickListener(this);

        //5c button
        five_cent =  findViewById(R.id.five_cent);
        five_cent.setOnClickListener(this);

        //wallet button
        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

        //Button to change to notes
        notes = findViewById(R.id.NoteBtn);
        notes.setOnClickListener(this);

        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();
    }

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

        Log.d("WALLET", "Coins");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }


    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("notes", walletData.getNotes());
        intent.putExtra("coins", walletData.getCoins());

        Log.d("WALLET", "Notes");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
        //startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        //Money Buttons
        if (v == (View) two_euro) {
            //Add €1 to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- €2 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin2e(walletData.getCoin2e()+1);
        } else if (v == (View) one_euro) {
            //Add €2 to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- €1 added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin1e(walletData.getCoin1e()+1);
        } else if (v == (View) fifty_cent) {
            //Add 50c to wallet & notify user of addition
            Toast.makeText(getApplicationContext(), "--- 50c added ---",
                    Toast.LENGTH_SHORT).show();
            walletData.setCoin50c(walletData.getCoin50c()+1);
        } else if (v == (View) twenty_cent) {
            //Add 20c to wallet & notify user of addition
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
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            startActivity(intent);
        }
    }
}
