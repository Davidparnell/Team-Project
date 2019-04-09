package com.moneyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
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
            item.setBalance("Balance: " + String.format("%.02f", walletHistory.get(i).getBalance()));
            item.setRegister("Bill: " + String.format("%.02f", walletHistory.get(i).getRegister()));

            //Setting transaction type, with transaction type image chosen.
            if(walletHistory.get(i).getRegister() == 0)
            {
                Drawable drawable = getResources().getDrawable(R.drawable.moneybutton);
                item.setType(drawable);
            }
            else
            {
                Drawable drawable = getResources().getDrawable(R.drawable.camerabutton);
                item.setType(drawable);
            }

            historyList.add(item);

            //test output to log, can be deleted and formatted later
            Log.d("HIS", walletHistory.get(i).getDate()+" "+String.format("%.02f", walletHistory.get(i).getBalance())+" "+String.format("%.02f", walletHistory.get(i).getRegister())+" ");
        }

        //Adapter to display data from ArrayList
        HistoryAdapter adapter = new HistoryAdapter(historyList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }
}
