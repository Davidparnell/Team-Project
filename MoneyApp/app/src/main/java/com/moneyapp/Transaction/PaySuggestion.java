package com.moneyapp.Transaction;

import android.media.Image;
import android.os.Bundle;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;
import com.moneyapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class PaySuggestion extends AppCompatActivity {
    Image notes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        //notes = findViewById(R.id.notes);
        AppDatabase database = Room.databaseBuilder(this, AppDatabase.class, "wallet")
                .allowMainThreadQueries()
                .build();

        WalletDAO walletDAO = database.getWalletDAO();
        //Date formatting
        Date date = Calendar.getInstance().getTime();;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);

        //Testing
        //walletDAO.deleteAll();
        WalletData walletData = walletDAO.getRecentWallet();
        //walletData

    }
}
