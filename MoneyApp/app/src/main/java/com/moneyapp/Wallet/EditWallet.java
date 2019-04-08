package com.moneyapp.Wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.moneyapp.MainActivity;
import com.moneyapp.R;

import java.util.Arrays;

public class EditWallet extends AppCompatActivity  implements View.OnClickListener{

    ImageButton confirm, cancel;

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

        Intent intent = getIntent();
        int numNotes[] = intent.getIntArrayExtra("notes");
        int numCoins[] = intent.getIntArrayExtra("coins");
        Log.d("WALLET", Arrays.toString(numNotes));
        Log.d("WALLET", Arrays.toString(numCoins));
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        //If confirm button pressed
        if(v == (View) confirm)
        {
            //Return to main
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //if cancel button pressed
        else if(v == (View) cancel)
        {
            //Return to wallet
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
