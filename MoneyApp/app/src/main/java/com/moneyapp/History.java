package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;

import java.util.List;

public class History extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //DB object
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        //DAO Object
        WalletDAO walletDAO = database.getWalletDAO();
        //List to store wallet data
        List<WalletData> walletHistory = walletDAO.getWalletHistory();

        //test output to log, can be deleted and formatted later
        for(int i = 0; i < walletHistory.size(); i++){
            String type;
            if(walletHistory.get(i).getRegister() == 0){
                type = "wallet change";
            }
            else{
                type = "transaction";
            }
            Log.d("HIS", walletHistory.get(i).getDate()+" "+String.format("%.02f", walletHistory.get(i).getBalance())+" "+String.format("%.02f", walletHistory.get(i).getRegister())+" "+type);
        }
    }
}
