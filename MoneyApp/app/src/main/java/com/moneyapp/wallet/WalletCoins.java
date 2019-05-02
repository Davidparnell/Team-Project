package com.moneyapp.wallet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import com.moneyapp.MainActivity;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;

import android.view.DragEvent;
import java.util.Arrays;
import android.content.ClipData;
import androidx.appcompat.app.AppCompatActivity;

/*
Allows for adding coins to the wallet by clicking or dragging.
Can switch between this and the Wallet activity keeping one instance of each in memory
passing anything added to the Wallet
 */

public class WalletCoins extends AppCompatActivity implements View.OnClickListener {

    //Initialize Layout Buttons
    ImageButton two_euro, one_euro, fifty_cent, twenty_cent,
            ten_cent, five_cent, wallet, notes;

    //Initialize DB objects
    WalletData walletData;//Initialize Wallet

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_coins);

        //€2 button
        two_euro = findViewById(R.id.two_euro);
        two_euro.setOnClickListener(this);

        //€1 button
        one_euro = findViewById(R.id.one_euro);
        one_euro.setOnClickListener(this);

        //50c button
        fifty_cent = findViewById(R.id.fifty_cent);
        fifty_cent.setOnClickListener(this);

        //20c button
        twenty_cent = findViewById(R.id.twenty_cent);
        twenty_cent.setOnClickListener(this);

        //10c button
        ten_cent = findViewById(R.id.ten_cent);
        ten_cent.setOnClickListener(this);

        //5c button
        five_cent =  findViewById(R.id.five_cent);
        five_cent.setOnClickListener(this);

        //Listeners for Drag and Drop
        two_euro.setOnLongClickListener(longClickListener);
        one_euro.setOnLongClickListener(longClickListener);
        fifty_cent.setOnLongClickListener(longClickListener);
        twenty_cent.setOnLongClickListener(longClickListener);
        ten_cent.setOnLongClickListener(longClickListener);
        five_cent.setOnLongClickListener(longClickListener);

        //wallet button
        wallet = findViewById(R.id.wallet);
        wallet.setOnClickListener(this);
        wallet.setOnDragListener(dragListener);

        //Button to change to notes
        notes = findViewById(R.id.NoteBtn);
        notes.setOnClickListener(this);

        walletData = new WalletData();
    }

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
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v)
    {
        final Animation bounceAnim = AnimationUtils.loadAnimation(this, R.anim.bounce);

        //Money Buttons
        if (v == (View) two_euro)
        {
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            two_euro.startAnimation(bounceAnim);
            //Add €1 to wallet & notify user of addition

            walletData.setCoin2e(walletData.getCoin2e()+1);
        }
        else if (v == (View) one_euro)
        {
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            one_euro.startAnimation(bounceAnim);
            //Add €2 to wallet & notify user of addition

            walletData.setCoin1e(walletData.getCoin1e()+1);
        }
        else if (v == (View) fifty_cent)
        {
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            fifty_cent.startAnimation(bounceAnim);
            //Add 50c to wallet & notify user of addition

            walletData.setCoin50c(walletData.getCoin50c()+1);
        }
        else if (v == (View) twenty_cent)
        {
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            twenty_cent.startAnimation(bounceAnim);
            //Add 20c to wallet & notify user of addition

            walletData.setCoin20c(walletData.getCoin20c()+1);
        }
        else if(v == (View) ten_cent)
        {
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            ten_cent.startAnimation(bounceAnim);
            //Add 10c to wallet, for now toast

            walletData.setCoin10c(walletData.getCoin10c()+1);
        }
        else if(v == (View) five_cent)
        {
            //Animation
            BounceInterpolator interpolator = new BounceInterpolator(0.05, 15);
            bounceAnim.setInterpolator(interpolator);
            five_cent.startAnimation(bounceAnim);
            //Add 5c to wallet, for now toast

            walletData.setCoin5c(walletData.getCoin5c()+1);
            //Options
        }
        else if (v == (View) wallet)
        {
            //Open EditWallet Activity
            Intent intent = new Intent(getApplicationContext(), EditWallet.class);
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);                          //go back to edit wallet if it exists, if not create it
        }
        else if (v == (View) notes)
        {
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            intent.putExtra("notes", walletData.getNotes());
            intent.putExtra("coins", walletData.getCoins());
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivityIfNeeded(intent, 0);                          //go back to Wallet if it exists, if not create it
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
                    break;

                case DragEvent.ACTION_DRAG_EXITED:
                    break;

                //Note dropped on wallet
                case DragEvent.ACTION_DROP:
                    //Find Item Dragged
                    final View view = (View) event.getLocalState();

                    if (view == (View) two_euro) {
                        //Add €1 to wallet & notify user of addition
                        walletData.setCoin2e(walletData.getCoin2e()+1);
                    } else if (view == (View) one_euro) {
                        //Add €2 to wallet & notify user of addition
                        walletData.setCoin1e(walletData.getCoin1e()+1);
                    } else if (view == (View) fifty_cent) {
                        //Add 50c to wallet & notify user of addition
                        walletData.setCoin50c(walletData.getCoin50c()+1);
                    } else if (view == (View) twenty_cent) {
                        //Add 20c to wallet & notify user of addition
                        walletData.setCoin20c(walletData.getCoin20c()+1);
                    } else if(view == (View) ten_cent){
                        //Add 10c to wallet, for now toast
                        walletData.setCoin10c(walletData.getCoin10c()+1);
                    } else if(view == (View) five_cent){
                        //Add 5c to wallet, for now toast
                        walletData.setCoin5c(walletData.getCoin5c()+1);
                    }
                    break;

                case DragEvent.ACTION_DRAG_ENDED:
                    break;
            }
            return true;
        }
    };
}
