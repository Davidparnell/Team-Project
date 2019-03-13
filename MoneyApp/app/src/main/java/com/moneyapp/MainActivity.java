package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.moneyapp.db.WalletDAO;
import com.moneyapp.db.Wallet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		
		com.moneyapp.db.AppDatabase database = Room.databaseBuilder(this, com.moneyapp.db.AppDatabase.class, "wallet")
                .allowMainThreadQueries()
                .build();

		WalletDAO walletDAO = database.getWalletDAO();

        Date date = Calendar.getInstance().getTime();;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

		Wallet wallet = new Wallet();
		wallet.setWallet(strDate, (float) 10.00, 10, 1, 0, 0, 1, "location1", "receipt1");
		walletDAO.insert(wallet);
		
		Log.d("WAL", walletDAO.getWalletHistory().toString());
    }
}
