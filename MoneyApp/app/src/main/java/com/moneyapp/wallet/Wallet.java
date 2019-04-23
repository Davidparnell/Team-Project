package com.moneyapp.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.moneyapp.MainActivity;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;

import java.util.Arrays;

public class Wallet extends AppCompatActivity implements View.OnClickListener {

    //Initialize Layout Buttons
    ImageButton five, ten, twenty, fifty, wallet, coins;

    //Initialize DB
    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.wallet );

        //€5 Button
        five = findViewById( R.id.euro5 );
        five.setOnClickListener( this );

        //€10 button
        ten = findViewById( R.id.euro10 );
        ten.setOnClickListener( this );

        //€20 button
        twenty = findViewById( R.id.euro20 );
        twenty.setOnClickListener( this );

        //€50 button
        fifty = findViewById( R.id.euro50 );
        fifty.setOnClickListener( this );

        //Wallet button
        wallet = findViewById( R.id.wallet );
        wallet.setOnClickListener( this );

        //Listeners for Drag and Drop
        /*
        fifty.setOnLongClickListener(longClickListener);
        twenty.setOnLongClickListener(longClickListener);
        ten.setOnLongClickListener(longClickListener);
        five.setOnLongClickListener(longClickListener);
        wallet.setOnDragListener(dragListener);
        */

        //Button to change to coins
        coins = findViewById( R.id.CoinBtn );
        coins.setOnClickListener( this );

        database = AppDatabase.getDatabase( getApplicationContext() );
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent( intent );
        setIntent( intent );
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent intent = getIntent();

        if (null != intent.getIntArrayExtra( "coins" )) { //Stop from getting nulls first time entering
            walletData.setNotes( intent.getIntArrayExtra( "notes" ) );
            walletData.setCoins( intent.getIntArrayExtra( "coins" ) );
        }

        if (intent.getStringExtra( "type" ) != null) {
            Log.d( "WALLET", intent.getStringExtra( "type" ) );
        }

        Log.d( "WALLET", "Notes" );
        Log.d( "WALLET", Arrays.toString( walletData.getNotes() ) );
        Log.d( "WALLET", Arrays.toString( walletData.getCoins() ) );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent( getApplicationContext(), MainActivity.class );
        startActivity( intent );
        finish();
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = getIntent();

        if(intent.getStringExtra("type").equals("walletCoins")) {
            intent.setClass(getApplicationContext(), WalletCoins.class);
        } else {
            intent.setClass(getApplicationContext(), EditWallet.class);
        }

        intent.putExtra("notes", walletData.getNotes());
        intent.putExtra("coins", walletData.getCoins());
        intent.putExtra("type", "wallet");

        Log.d("WALLET", "Notes");
        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
        startActivity(intent);
    }*/

    @Override
    //Functions after a button is pressed
    public void onClick(View v) {
        //Money Buttons
        if (v == (View) five) {
            //Add 5 to wallet & notify user of addition
            Toast.makeText( getApplicationContext(), "--- 5 added ---",
                    Toast.LENGTH_SHORT ).show();
            walletData.setNote5( walletData.getNote5() + 1 );
        } else if (v == (View) ten) {
            //Add 10 to wallet & notify user of addition
            Toast.makeText( getApplicationContext(), "--- 10 added ---",
                    Toast.LENGTH_SHORT ).show();
            walletData.setNote10( walletData.getNote10() + 1 );
        } else if (v == (View) twenty) {
            //Add 20 to wallet & notify user of addition
            Toast.makeText( getApplicationContext(), "--- 20 added ---",
                    Toast.LENGTH_SHORT ).show();
            walletData.setNote20( walletData.getNote20() + 1 );
        } else if (v == (View) fifty) {
            //Add 50 to wallet & notify user of addition
            Toast.makeText( getApplicationContext(), "--- 50 added ---",
                    Toast.LENGTH_SHORT ).show();
            walletData.setNote50( walletData.getNote50() + 1 );
            //Options
        } else if (v == (View) wallet) {
            //Open EditWallet Activity
            Intent intent = new Intent( getApplicationContext(), EditWallet.class );
            intent.putExtra( "notes", walletData.getNotes() );
            intent.putExtra( "coins", walletData.getCoins() );
            intent.putExtra( "type", "wallet" );
            startActivity( intent );
        } else if (v == (View) coins) {
            //Change to coins activity
            Intent intent = new Intent( getApplicationContext(), WalletCoins.class );
            intent.putExtra( "notes", walletData.getNotes() );
            intent.putExtra( "coins", walletData.getCoins() );
            intent.putExtra( "type", "wallet" );
            startActivity( intent );
        }
    }


    View.OnLongClickListener longClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            ClipData data = ClipData.newPlainText( "", "" );

            View.DragShadowBuilder build = new View.DragShadowBuilder( v );
            v.startDrag( data, build, v, 0 );

            return true;
        }
    };

    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch(dragEvent)
            {
                case DragEvent.ACTION_DRAG_ENTERED:
                    //Find Item Dragged
                    final View view = (View) event.getLocalState();

                    if (v == (View) five) {
                        //Add 5 to wallet & notify user of addition
                        Toast.makeText( getApplicationContext(), "--- 5 added ---",
                                Toast.LENGTH_SHORT ).show();
                        walletData.setNote5( walletData.getNote5() + 1 );
                    } else if (v == (View) ten) {
                        //Add 10 to wallet & notify user of addition
                        Toast.makeText( getApplicationContext(), "--- 10 added ---",
                                Toast.LENGTH_SHORT ).show();
                        walletData.setNote10( walletData.getNote10() + 1 );
                    } else if (v == (View) twenty) {
                        //Add 20 to wallet & notify user of addition
                        Toast.makeText( getApplicationContext(), "--- 20 added ---",
                                Toast.LENGTH_SHORT ).show();
                        walletData.setNote20( walletData.getNote20() + 1 );
                    } else if (v == (View) fifty) {
                        //Add 50 to wallet & notify user of addition
                        Toast.makeText( getApplicationContext(), "--- 50 added ---",
                                Toast.LENGTH_SHORT ).show();
                        walletData.setNote50( walletData.getNote50() + 1 );
                    }
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                //Note dropped on wallet
                case DragEvent.ACTION_DROP:
                    break;
            }
            return true;
        }
    };
}