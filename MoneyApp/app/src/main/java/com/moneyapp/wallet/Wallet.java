package com.moneyapp.wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.moneyapp.MainActivity;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;

import android.view.DragEvent;
import java.util.Arrays;
import android.content.ClipData;

public class Wallet extends AppCompatActivity implements View.OnClickListener {

    //Initialize Layout Buttons
    ImageButton five, ten, twenty, fifty, wallet, coins;

    //Initialize DB
    AppDatabase database;//Initialize AppDatabase
    WalletDAO walletDAO;//Initialize DAO
    WalletData walletData;//Initialize Wallet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //€5 Button
        five = findViewById(R.id.euro5);
        five.setOnClickListener(this);

        //€10 button
        ten = findViewById(R.id.euro10);
        ten.setOnClickListener(this);

        //€20 button
        twenty = findViewById(R.id.euro20);
        twenty.setOnClickListener(this);

        //€50 button
        fifty = findViewById(R.id.euro50);
        fifty.setOnClickListener(this);

        //Listeners for Drag and Drop
        fifty.setOnLongClickListener(longClickListener);
        twenty.setOnLongClickListener(longClickListener);
        ten.setOnLongClickListener(longClickListener);
        five.setOnLongClickListener(longClickListener);

        //Wallet button
        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);
        wallet.setOnDragListener(dragListener);

        //Button to change to coins
        coins = findViewById(R.id.CoinBtn);
        coins.setOnClickListener(this);

        database = AppDatabase.getDatabase(getApplicationContext());
        walletDAO = database.getWalletDAO();
        walletData = walletDAO.getRecentWallet();
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

        if(null != intent.getIntArrayExtra("coins")) { //Stop from getting nulls first time entering
            walletData.setNotes(intent.getIntArrayExtra("notes"));
            walletData.setCoins(intent.getIntArrayExtra("coins"));
        }

        Log.d("WALLET", Arrays.toString(walletData.getNotes()));
        Log.d("WALLET", Arrays.toString(walletData.getCoins()));
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        final Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        //Money Buttons
        if (v == (View) five)
        {
            //Add 5 to wallet & notify user of addition
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            five.startAnimation(bounceAnim);

            walletData.setNote5(walletData.getNote5()+1);
        }
        else if (v == (View) ten)
        {
            //Add 10 to wallet & notify user of addition
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            ten.startAnimation(bounceAnim);

            walletData.setNote10(walletData.getNote10()+1);
        }
        else if (v == (View) twenty)
        {
            //Add 20 to wallet & notify user of addition
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            twenty.startAnimation(bounceAnim);

            walletData.setNote20(walletData.getNote20()+1);
        }
        else if (v == (View) fifty)
        {
            //Add 50 to wallet & notify user of addition
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            fifty.startAnimation(bounceAnim);

            walletData.setNote50(walletData.getNote50()+1);
            //Options
        }
        else if (v == (View) wallet)
        {
            //Open EditWallet Activity
            Intent intent = new Intent(getApplicationContext(), EditWallet.class);
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);              //go back to edit if it exists, if not create it
        }
        else if (v == (View) coins)
        {
            //Change to coins activity
            Intent intent = new Intent(getApplicationContext(), WalletCoins.class);
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);             //go back to walletCoins if it exists, if not create it
        }
    }View.OnLongClickListener longClickListener = v -> {
        ClipData data = ClipData.newPlainText( "", "" );

        //Builds shadow of dragged image
        View.DragShadowBuilder build = new View.DragShadowBuilder( v );
        v.startDrag( data, build, v, 0 );

        return true;
    };

    //Drag & Drop
    View.OnDragListener dragListener = new View.OnDragListener() {
        @Override
        public boolean onDrag(View v, DragEvent event) {
            int dragEvent = event.getAction();

            switch(dragEvent)
            {
                case DragEvent.ACTION_DRAG_ENTERED:
                    //Find Item Dragged
                    final View view = (View) event.getLocalState();

                    if (view == (View) five) {
                        //Add 5 to wallet & notify user of addition
                        walletData.setNote5( walletData.getNote5() + 1 );
                    } else if (view == (View) ten) {
                        //Add 10 to wallet & notify user of addition
                        walletData.setNote10( walletData.getNote10() + 1 );
                    } else if (view == (View) twenty) {
                        //Add 20 to wallet & notify user of addition
                        walletData.setNote20( walletData.getNote20() + 1 );
                    } else if (view == (View) fifty) {
                        //Add 50 to wallet & notify user of addition
                        walletData.setNote50( walletData.getNote50() + 1 );
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }
            return true;
        }
    };
}