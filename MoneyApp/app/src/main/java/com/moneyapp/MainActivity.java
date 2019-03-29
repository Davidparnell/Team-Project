package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.util.Log;

import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Database object
		com.moneyapp.Database.AppDatabase database = Room.databaseBuilder(this, com.moneyapp.Database.AppDatabase.class, "wallet")
                .allowMainThreadQueries()
                .build();

		WalletDAO walletDAO = database.getWalletDAO();
        //Date formatting
        Date date = Calendar.getInstance().getTime();;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        //Testing
        //walletDAO.deleteAll();
		WalletData walletData = new WalletData();
		walletData.setWalletOptions(strDate, (float) 10.00, 10,"location1", "receipt1");
		walletData.setNotes(0, 0, 1, 0);
		walletData.setCoins(1,0,0,0,0,0);
		walletDAO.insert(walletData);

		Log.d("WAL", walletDAO.getRecentWallet().toString());
        Log.d("WAL", walletDAO.getWalletHistory().toString());
    }
}
