package com.moneyapp.Transaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;
import com.moneyapp.R;

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

        WalletData walletData = walletDAO.getRecentWallet();
        Intent intent = getIntent();
        float register = Float.parseFloat(intent.getStringExtra("register"));
        int notes[] = generateSuggestion(register, walletData.getNotes());
    }

    public int[] generateSuggestion(float register, int[] notes){
        int rReg = (int)5*(Math.round(register/5));
        //ideal path


        while(rReg != 0){
            if(rReg >= 50){
                if(notes[0] > 0){

                }
            }
        }

        Log.d("REG", String.valueOf(rReg));
        return notes;
    }
}