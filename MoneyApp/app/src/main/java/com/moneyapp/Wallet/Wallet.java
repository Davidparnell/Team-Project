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

    ImageButton five, ten, twenty, fifty, confirm, wallet, coins;
    AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
    WalletDAO walletDAO = database.getWalletDAO();
    WalletData walletData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet);

        five = findViewById(R.id.euro5);
        five.setOnClickListener(this);

        ten = findViewById(R.id.euro10);
        ten.setOnClickListener(this);

        twenty = findViewById(R.id.euro20);
        twenty.setOnClickListener(this);

        fifty = findViewById(R.id.euro50);
        fifty.setOnClickListener(this);

        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);

        coins = findViewById(R.id.CoinBtn);
        coins.setOnClickListener(this);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v) {
        //Money Buttons
        if (v == (View) five) {
            //Add 5 to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 5 added ---",
                    Toast.LENGTH_SHORT).show();

        } else if (v == (View) ten) {
            //Add 10 to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 10 added ---",
                    Toast.LENGTH_SHORT).show();
        } else if (v == (View) twenty) {
            //Add 20 to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 20 added ---",
                    Toast.LENGTH_SHORT).show();
        } else if (v == (View) fifty) {
            //Add 50 to wallet, for now toast
            Toast.makeText(getApplicationContext(), "--- 50 added ---",
                    Toast.LENGTH_SHORT).show();
        } else if (v == (View) wallet) {
            //Open EditWallet Activity
            Intent intent = new Intent(getApplicationContext(), EditWallet.class);
            //intent.putExtra("reg", );
            startActivity(intent);
        }
        else if (v == (View) coins)
        {
            //Change to coins
            Intent intent = new Intent(getApplicationContext(), WalletCoins.class);
            startActivity(intent);
        }
    }

}