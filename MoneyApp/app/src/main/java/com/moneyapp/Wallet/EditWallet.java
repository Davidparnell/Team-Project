package com.moneyapp.Wallet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.moneyapp.MainActivity;
import com.moneyapp.R;

public class EditWallet extends AppCompatActivity  implements View.OnClickListener{

    ImageButton Confirm, Cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_wallet);

        //Wallet selection is ok
        Confirm = findViewById(R.id.ConfirmWallet);
        Confirm.setOnClickListener(this);

        //Wallet selection is incorrect, stuff needs to be added
        Cancel = findViewById(R.id.CancelWallet);
        Cancel.setOnClickListener(this);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        //If confirm button pressed
        if(v == (View) Confirm)
        {
            //Return to main
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        //if cancel button pressed
        else if(v == (View) Cancel)
        {
            //Return to wallet
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            startActivity(intent);
        }
    }
}
