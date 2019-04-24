package com.moneyapp.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moneyapp.MainActivity;
import com.moneyapp.MoneyListAdapter;
import com.moneyapp.MoneyListData;
import com.moneyapp.R;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.widget.AdapterView;
import android.widget.Toast;


public class EditWallet extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemClickListener {

    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    final float[] nValues = {50f, 20f, 10f, 5f};
    final float[] cValues = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};
    int i = 0;
    private ListView listView;
    private List<MoneyListData> moneyList;
    MoneyListAdapter adapter;   //set on resume only because on resume happens after on create regardless

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_wallet);

        //Database set up
        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();

        listView = findViewById(R.id.suggestionList);

        listView.setOnItemClickListener(this);
        moneyList = new ArrayList<>();

        //Floating button used to go to next activity.
        FloatingActionButton confirm = findViewById(R.id.floating_tick);
        confirm.setOnClickListener(this);

        FloatingActionButton exit = findViewById(R.id.floating_exit);
        exit.setOnClickListener(this);
    }

    public List<MoneyListData> createMoneyList(int[] notes, int[] coins){
        ArrayList<String> cash = new ArrayList<String>();
        moneyList = new ArrayList<>();

        for(int i = 0; i < notes.length; i++){
            for(int j = 0; j < notes[i]; j++){
                //Add note value as cash to the cash ArrayList
                cash.add(String.valueOf(nValues[i]));
            }
        }

        for(int i = 0; i < coins.length; i++){
            for(int j = 0; j < coins[i]; j++){
                //Add coin value as cash to the cash ArrayList
                cash.add(String.valueOf(cValues[i]));
            }
        }

        //Creating drawables from the cash ArrayList values.
        for (int i = 0; i < cash.size(); i++)
        {
            MoneyListData item = new MoneyListData();

            switch (cash.get(i)) {
                case "50.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.fifty_euro);
                    item.setCash(drawable);
                    break;
                }
                case "20.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.twenty_euro);
                    item.setCash(drawable);
                    break;
                }
                case "10.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.ten_euro);
                    item.setCash(drawable);
                    break;
                }
                case "5.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.five_euro);
                    item.setCash(drawable);
                    break;
                }
                case "2.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.two_euro);
                    item.setCash(drawable);
                    break;
                }
                case "1.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.one_euro);
                    item.setCash(drawable);
                    break;
                }
                case "0.5": {
                    Drawable drawable = getResources().getDrawable(R.drawable.fifty_cent);
                    item.setCash(drawable);
                    break;
                }
                case "0.2": {
                    Drawable drawable = getResources().getDrawable(R.drawable.twenty_cent);
                    item.setCash(drawable);
                    break;
                }
                case "0.1": {
                    Drawable drawable = getResources().getDrawable(R.drawable.ten_cent);
                    item.setCash(drawable);
                    break;
                }
                case "0.05": {
                    Drawable drawable = getResources().getDrawable(R.drawable.five_cent);
                    item.setCash(drawable);
                    break;
                }
            }
            //Insert calculated drawable into the ContentsList ArrayList.
            moneyList.add(item);
        }
        return moneyList;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Log.d("WALLET", String.valueOf(i));//test to see if activity ended
        i++;
        walletData.setNotes(intent.getIntArrayExtra("notes"));
        walletData.setCoins(intent.getIntArrayExtra("coins"));

        moneyList = createMoneyList(walletData.getNotes(),walletData.getCoins());

        adapter = new MoneyListAdapter(moneyList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Log.d("WALLET", "Edit");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Wallet.class);   //goes back to wallet
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("notes", walletData.getNotes());
        intent.putExtra("coins", walletData.getCoins());
        startActivityIfNeeded(intent, 0);
        /*
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class); //or back to main
        startActivity(intent);
        finish();*/
    }

    //Insert current wallet to database
    public void databaseInsert(){
        Date date = Calendar.getInstance().getTime();
        int[] notes = walletData.getNotes();//Note list
        int[] coins = walletData.getCoins();//Coin list
        float balance = notes[0] * 50f + notes[1] * 20f + notes[2] * 10f + notes[3] * 5f;
        balance += coins[0] * 2.00f + coins[1] * 1.00f + coins[2] * 0.50f + coins[3] * 0.20f + coins[4] * 0.10f + coins[5] * 0.05f;

        walletData.setWalletOptions(date, balance, 0);
        walletDAO.insert(walletData);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.floating_tick:
            {
                //Insert data into database and return home
                databaseInsert();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.floating_exit:
            {
                //Return home
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Log.d("WALLET", "Removed: " + moneyList.get(position));

        MoneyListData item = moneyList.get(position);
        moneyList.remove(item);

        adapter = new MoneyListAdapter(moneyList, this);
        listView.setAdapter(adapter);


        Toast.makeText(getApplicationContext(), moneyList.get(position) +"Removed",
                Toast.LENGTH_SHORT).show();
    }
}
