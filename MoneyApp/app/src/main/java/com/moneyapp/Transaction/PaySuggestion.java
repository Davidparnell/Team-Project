package com.moneyapp.Transaction;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;

import com.moneyapp.Database.AppDatabase;
import com.moneyapp.Database.WalletDAO;
import com.moneyapp.Database.WalletData;
import com.moneyapp.R;

import java.lang.reflect.Array;
import java.util.Arrays;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

public class PaySuggestion extends AppCompatActivity {
    int notes[];
    int coins[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);

        //notes = findViewById(R.id.notes);
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());

        WalletDAO walletDAO = database.getWalletDAO();

        WalletData walletData = walletDAO.getRecentWallet();
        Intent intent = getIntent();
        float register = Float.parseFloat(intent.getStringExtra("register"));
        notes = walletData.getNotes();
        coins = walletData.getCoins();
        /*Log.d("REG", String.valueOf(notes[0]));//2
        Log.d("REG", String.valueOf(notes[1]));//1
        Log.d("REG", String.valueOf(notes[2]));//1
        Log.d("REG", String.valueOf(notes[3]));//1*/
        int changeNotes[] = generateSuggestion(register);
    }

    public int[] generateSuggestion(float register){
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};
        int roundReg = 5 *(Math.round(register/5));//rounded up in 5's for notes(5 being lowest) eg. 23.50 = 25
        int regTemp = roundReg;
        int notes[] = getNotes();//notes currently in wallet
        int coins[] = getCoins();//coins currently in wallet
        float balanceNotes = notes[0]*50 + notes[1]*20 + notes[2]*10 + notes[3]*5; //total with notes only
        float balanceAll = (float) (coins[0]*2 + coins[1] + coins[2]*0.50 + coins[3]*0.20 + coins[4]*0.10 + coins[5]*0.05 + balanceNotes);//full total
        int changeNotes[] = {0,0,0,0};
        int changeCoins[] = {0,0,0,0,0,0};
        int i = 0;

        Log.d("REG", String.valueOf(balanceNotes));
        if(5 >= register){//coins only below 5 euro

        }else if(balanceNotes >= register) {
            //ideal path
            Log.d("REG", "ideal path");
            while (regTemp > 0) {
                if (regTemp >= nValues[i] && notes[i] != 0) {
                    regTemp -= nValues[i];
                    notes[i]--;
                    changeNotes[i]++;
                }
                else{
                    i++;
                }

                if(i==4 && regTemp > 0){//non ideal eg. has 50 note only but needs 25
                    regTemp = roundReg;
                    Arrays.fill(changeNotes, 0);
                    notes = getNotes();
                    i = 3;
                    //2 loops accounting for overflow in each, pick smaller
                    //also second scan through looking for redundancy
                    while (regTemp > 0){
                        if ( notes[i] != 0) {
                            regTemp -= nValues[i];
                            notes[i]--;
                            changeNotes[i]++;
                            //Log.d("REG", String.valueOf(i));
                        }
                        else{

                            i--;
                            if(i < 0){
                                regTemp = 0;
                            }
                        }
                    }
                }
            }
            //Log.d("REG", String.valueOf(temp));
            Log.d("REG", String.valueOf(changeNotes[0]));//2
            Log.d("REG", String.valueOf(changeNotes[1]));//1
            Log.d("REG", String.valueOf(changeNotes[2]));//1
            Log.d("REG", String.valueOf(changeNotes[3]));//1

        }else if(balanceAll >= register){
            Log.d("REG", "coins path");

        }
        else{
            Log.d("REG", "Not enough money");
        }

        Log.d("REG", String.valueOf(roundReg));
        return notes;
    }

    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}
}