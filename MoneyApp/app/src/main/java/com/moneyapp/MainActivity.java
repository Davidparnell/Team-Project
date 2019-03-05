package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.moneyapp.db.WalletDAO;
import com.moneyapp.db.Wallet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		com.moneyapp.db.AppDatabase database = Room.databaseBuilder(this, com.moneyapp.db.AppDatabase.class, "wallet")
                .allowMainThreadQueries()
                .build();

		WalletDAO walletDAO = database.getWalletDAO();
    }
}
