package com.moneyapp.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

/*
Activity Listing all money in wallet allowing removing individual notes/coins
Has option to cancel or confirm to add or exit without adding to the database
 */

public class EditWallet extends AppCompatActivity  implements View.OnClickListener, AdapterView.OnItemClickListener {

    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    final float[] nValues = {50f, 20f, 10f, 5f};
    final float[] cValues = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};

    private ListView listView;
    private List<MoneyListData> moneyList; //for listView
    MoneyListAdapter adapter;   //set on resume only because on resume happens after on create regardless

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_wallet);

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

        SwipeListener touchListener = makeSwipeListener();
        listView.setOnTouchListener(touchListener);
    }

    public SwipeListener makeSwipeListener(){
        return new SwipeListener(listView, new SwipeListener.DismissCallbacks() {
            @Override
            public boolean canDismiss(int position) {
                        return true;
                    }
                    /*
                    when item's dismissed, remove that item from the Moneylist
                    and update the  listView
                    */
                    @Override
                    public void onDismiss(ListView listView, int[] reverseSortedPositions) {
                        for (int position : reverseSortedPositions) {

                            //Remove from list
                            MoneyListData item = moneyList.get(position);
                            moneyList.remove(item);

                            //Update list view
                            adapter = new MoneyListAdapter(moneyList, getApplicationContext());
                            listView.setAdapter(adapter);

                            //Remove the item from the wallet
                            int[] wallet;
                            if (item.getType().equals("note")) {
                                wallet = walletData.getNotes();
                                wallet[item.getIndex()]--;
                                walletData.setNotes(wallet);
                            } else if (item.getType().equals("coin")) {
                                wallet = walletData.getCoins();
                                wallet[item.getIndex()]--;
                                walletData.setCoins(wallet);
                            }
                        }
                    }
                });
    }

    //Create money list and populate
    public List<MoneyListData> createMoneyList(int[] notes, int[] coins){
        ArrayList<String> cash = new ArrayList<String>();
        moneyList = new ArrayList<>();

        //add notes
        for(int i = 0; i < notes.length; i++){
            for(int j = 0; j < notes[i]; j++){
                //Add note value as cash to the cash ArrayList
                cash.add(String.valueOf(nValues[i]));
            }
        }

        //add coins
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
                    item.setType("note");
                    item.setIndex(0);
                    break;
                }
                case "20.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.twenty_euro);
                    item.setCash(drawable);
                    item.setType("note");
                    item.setIndex(1);
                    break;
                }
                case "10.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.ten_euro);
                    item.setCash(drawable);
                    item.setType("note");
                    item.setIndex(2);
                    break;
                }
                case "5.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.five_euro);
                    item.setCash(drawable);
                    item.setType("note");
                    item.setIndex(3);
                    break;
                }
                case "2.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.two_euro);
                    item.setCash(drawable);
                    item.setType("coin");
                    item.setIndex(0);
                    break;
                }
                case "1.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.one_euro);
                    item.setCash(drawable);
                    item.setType("coin");
                    item.setIndex(1);
                    break;
                }
                case "0.5": {
                    Drawable drawable = getResources().getDrawable(R.drawable.fifty_cent);
                    item.setCash(drawable);
                    item.setType("coin");
                    item.setIndex(2);
                    break;
                }
                case "0.2": {
                    Drawable drawable = getResources().getDrawable(R.drawable.twenty_cent);
                    item.setCash(drawable);
                    item.setType("coin");
                    item.setIndex(3);
                    break;
                }
                case "0.1": {
                    Drawable drawable = getResources().getDrawable(R.drawable.ten_cent);
                    item.setCash(drawable);
                    item.setType("coin");
                    item.setIndex(4);
                    break;
                }
                case "0.05": {
                    Drawable drawable = getResources().getDrawable(R.drawable.five_cent);
                    item.setCash(drawable);
                    item.setType("coin");
                    item.setIndex(5);
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
        walletData.setNotes(intent.getIntArrayExtra("notes"));
        walletData.setCoins(intent.getIntArrayExtra("coins"));

        moneyList = createMoneyList(walletData.getNotes(),walletData.getCoins());

        adapter = new MoneyListAdapter(moneyList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), Wallet.class);   //goes back to wallet
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("notes", walletData.getNotes());
        intent.putExtra("coins", walletData.getCoins());
        startActivityIfNeeded(intent, 0);
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
                //Return home but dont insert into DB
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }
}
