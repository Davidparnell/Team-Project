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

        int changeNotes[] = generateSuggestion(register);

        Log.d("REG", String.valueOf(changeNotes[0]));
        Log.d("REG", String.valueOf(changeNotes[1]));
        Log.d("REG", String.valueOf(changeNotes[2]));
        Log.d("REG", String.valueOf(changeNotes[3]));
    }

    //takes 2 change arrays to compare + cleans up 2nd after 2nd algorithm. register for coin compare needs to specific to coins
    public int[] compareChange(float register, int change[], int change2[]) {
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};
        float changeTotal = 0;
        float changeTotal2 = 0;
        if(change.length == 4) {
            changeTotal = change[0] * nValues[0] + change[1] * nValues[1] + change[2] * nValues[2] + change[3] * nValues[3];
            changeTotal2 = change2[0] * nValues[0] + change2[1] * nValues[1] + change2[2] * nValues[2] + change2[3] * nValues[3];
        }
        else {
            changeTotal = change[0] * cValues[0] + change[1] + change[2] * cValues[2] + change[3] * cValues[3] + change[4] * cValues[4] + change[5] * cValues[5];
            changeTotal = change2[0] * cValues[0] + change2[1] + change2[2] * cValues[2] + change2[3] * cValues[3] + change2[4] * cValues[4] + change2[5] * cValues[5];
        }
        float overflow = changeTotal - register;
        float overflow2 = changeTotal2 - register;
        int i = 0;

        //Clean up 2nd change array, algorith for 2nd sometimes is better but has extra unneeded notes
        while(i < change2.length) {
            if (nValues[i] < overflow2 && change2[i] > 0) {
                overflow2 -= nValues[i];
                change2[i]--;
            } else{
                i++;
            }
        }
        changeTotal2 = change2[0] * nValues[0] + change2[1] * nValues[1] + change2[2] * nValues[2] + change2[3] * nValues[3];

        //check cleanup happened correctly, so enough change is given
        if(changeTotal2 < register){
            return change;
        } else if(overflow > overflow2) {
            return change2;
        } else if(overflow < overflow2) {
            return change;
        } else if(overflow == overflow2) {
            int total = 0;
            int total2 = 0;

            for(i = 0; i < change.length; i++){
                total += change[i];
                total += change2[i];
            }

            if(total > total2){ return change2; }
            else{ return change; }
        }

        return change;
    }

    public int[] generateSuggestion(float register){
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};
        float balanceNotes = notes[0] * nValues[0] + notes[1] * nValues[1] + notes[2] * nValues[2] + notes[3] * nValues[3]; //total with notes only
        float balanceAll = coins[0] * cValues[0] + coins[1] + coins[2] * cValues[2] + coins[3] * cValues[3] + coins[4] * cValues[4] + coins[5] * cValues[5] + balanceNotes;//full total
        //int roundReg = 5 *(Math.round(register/5));//rounded up in 5's for notes(5 being lowest) eg. 23.50 = 25
        float regTemp = register;
        int notes[] = getNotes();//notes currently in wallet
        int coins[] = getCoins();//coins currently in wallet
        int changeNotes[] = {0,0,0,0};
        int changeNotes2[] = {0,0,0,0};
        int changeCoins[] = {0,0,0,0,0,0};
        int i = 0;
        int j = 0;
        int x = 0;

        Log.d("REG", String.valueOf(balanceNotes));
        if(5 > register){//coins only below 5 euro

        }else if(balanceNotes >= register) {
            //ideal path
            while (regTemp > 0) {
                if (regTemp >= nValues[i] && notes[i] != 0) {
                    regTemp -= nValues[i];
                    notes[i]--;
                    changeNotes[i]++;
                }
                else{
                    i++;
                }

                if(i==notes.length){//non ideal eg. has 50 note only but needs 25
                    regTemp = register;
                    Arrays.fill(changeNotes, 0);
                    notes = getNotes();
                    i = 0;

                    while (regTemp > 0){
                        if ( notes[i] != 0) {
                            regTemp -= nValues[i];
                            notes[i]--;
                            changeNotes[i]++;
                        }
                        else{

                            i++;
                            if(i < 0){
                                regTemp = 0;
                            }
                        }
                    }
                }
            }

            regTemp = register;
            notes = getNotes();
            while(regTemp > 0){
                i=0;
                j=0;
                while(i != notes.length){
                    if(notes[i] != 0 ){
                        x=i;
                        if(regTemp < nValues[j] && regTemp > nValues[i]) {
                            j=i;
                        }
                    }
                    else if(nValues[x] > regTemp){
                        j=x;
                    }
                    i++;
                }

                regTemp -= nValues[j];
                notes[j]--;
                changeNotes2[j]++;
            }

            /*Log.d("REG", String.valueOf(changeNotes2[0]));
            Log.d("REG", String.valueOf(changeNotes2[1]));
            Log.d("REG", String.valueOf(changeNotes2[2]));
            Log.d("REG", String.valueOf(changeNotes2[3]));*/
            changeNotes = compareChange(register, changeNotes, changeNotes2);

        }else if(balanceAll >= register){
            Log.d("REG", "coins path");
        }
        else{
            Log.d("REG", "Not enough money");
        }
        return changeNotes;
    }

    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}
}