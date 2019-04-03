package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;

import java.util.ArrayList;
import java.util.List;

public class History extends AppCompatActivity
{
    private ListView listView;
    private List<HistoryData> historyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        listView = findViewById(R.id.historyList);
        historyList = new ArrayList<>();

        //DB object
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        //DAO Object
        WalletDAO walletDAO = database.getWalletDAO();
        //List to store wallet data
        List<WalletData> walletHistory = walletDAO.getWalletHistory();

        //Adding items into the History ListView
        for(int i = 0; i < walletHistory.size(); i++)
        {
            HistoryData item = new HistoryData();
            item.setDate(walletHistory.get(i).getDate());
            item.setBalance(String.format("%.02f", walletHistory.get(i).getBalance()));
            item.setRegister(String.format("%.02f", walletHistory.get(i).getRegister()));

            String type;
            if(walletHistory.get(i).getRegister() == 0)
            {
                type = "wallet change";
                item.setType(type);
            }
            else
            {
                type = "transaction";
                item.setType(type);
            }

            historyList.add(item);

            //test output to log, can be deleted and formatted later
            Log.d("HIS", walletHistory.get(i).getDate()+" "+String.format("%.02f", walletHistory.get(i).getBalance())+" "+String.format("%.02f", walletHistory.get(i).getRegister())+" "+type);
        }

        //Adapter to display data from ArrayList
        HistoryAdapter adapter = new HistoryAdapter(historyList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
