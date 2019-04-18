package com.moneyapp.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.moneyapp.MainActivity;
import com.moneyapp.R;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.transaction.SuggestionData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditWallet extends AppCompatActivity  implements View.OnClickListener{

    ImageButton confirm, cancel;

    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    int notes[];
    int coins[];
    int path = 0;

    private ListView listView;
    private List<ContentsData> ContentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_wallet);

        //Wallet selection is ok
        confirm = findViewById(R.id.ConfirmWallet);
        confirm.setOnClickListener(this);

        //Wallet selection is incorrect, stuff needs to be added
        cancel = findViewById(R.id.CancelWallet);
        cancel.setOnClickListener(this);

        //Database set up
        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();

        listView = findViewById(R.id.WalletContents);
        ContentList = new ArrayList<>();

        Intent intent = getIntent();

        notes = walletData.getNotes();
        coins = walletData.getCoins();

        ArrayList<String> cash = new ArrayList<String>();
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};

        //Creating drawables from the cash ArrayList values.
        for (int i = 0; i < cash.size(); i++) {
            ContentsData item = new ContentsData();

            if (cash.get( i ).equals( "50.0" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.fifty_euro );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "20.0" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.twenty_euro );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "10.0" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.ten_euro );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "5.0" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.five_euro );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "2.0" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.two_euro );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "1.0" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.one_euro );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "0.5" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.fifty_cent );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "0.2" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.twenty_cent );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "0.1" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.ten_cent );
                item.setCash( drawable );
            } else if (cash.get( i ).equals( "0.05" )) {
                Drawable drawable = getResources().getDrawable( R.drawable.five_cent );
                item.setCash( drawable );
            }
            //Insert calculated drawable into the suggestionList ArrayList.
            ContentList.add(item);
        }

        Log.d("REG", cash.toString());


        ContentsAdapter adapter = new ContentsAdapter(ContentList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

        Log.d("WALLET", "Edit");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();

        if(intent.getStringExtra("type").equals("wallet")) {
            intent.setClass(getApplicationContext(), Wallet.class);
        } else {
            intent.setClass(getApplicationContext(), WalletCoins.class);
        }

        intent.putExtra("notes", walletData.getNotes());
        intent.putExtra("coins", walletData.getCoins());
        intent.putExtra("type", "editWallet");
        startActivity(intent);
        Log.d("WALLET", "Notes");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }*/

    //Insert current wallet to database
    public void databaseInsert(){
        Date date = Calendar.getInstance().getTime();
        int notes[] = walletData.getNotes();//Note list
        int coins[] = walletData.getCoins();//Coin list
        float balance = notes[0] * 50f + notes[1] * 20f + notes[2] * 10f + notes[3] * 5f;
        balance += coins[0] * 2.00f + coins[1] * 1.00f + coins[2] * 0.50f + coins[3] * 0.20f + coins[4] * 0.10f + coins[5] * 0.05f;

        walletData.setWalletOptions(date, balance, 0);
        walletDAO.insert(walletData);
    }

    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}


    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        //If confirm button pressed
        if(v == (View) confirm)
        {
            databaseInsert();
            //Return to main & add to database
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        //if cancel button pressed
        else if(v == (View) cancel)
        {
            //Return to wallet
            //onBackPressed();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

    }
}
