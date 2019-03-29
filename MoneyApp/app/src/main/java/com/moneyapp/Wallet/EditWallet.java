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

        Confirm = findViewById(R.id.ConfirmWallet);
        Confirm.setOnClickListener(this);

        Cancel = findViewById(R.id.CancelWallet);
        Cancel.setOnClickListener(this);
    }

    @Override
    //Functions after a button is pressed
    public void onClick(View v)
    {
        if(v == (View) Confirm)
        {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }

        else if(v == (View) Cancel)
        {
            Intent intent = new Intent(getApplicationContext(), Wallet.class);
            startActivity(intent);
        }
    }
}
