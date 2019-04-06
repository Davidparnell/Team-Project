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
import java.math.BigDecimal;
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

        int change[] = generateSuggestion(register);
        for(int i = 0; i< change.length; i++) {
            Log.d("REG", "["+String.valueOf(i)+"] - "+String.valueOf(change[i]));
        }

    }

    public int[] generateSuggestion(float registerFloat){
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};
        BigDecimal balanceNotes = BigDecimal.valueOf(notes[0] * nValues[0] + notes[1] * nValues[1] + notes[2] * nValues[2] + notes[3] * nValues[3]);
        BigDecimal balanceCoins = BigDecimal.valueOf(coins[0] * cValues[0] + coins[1] + coins[2] * cValues[2] + coins[3] * cValues[3] + coins[4] * cValues[4] + coins[5] * cValues[5]);
        int changeNotes[] = {0,0,0,0};
        int changeNotes2[] = {0,0,0,0};
        int changeCoins[] = {0,0,0,0,0,0};
        int changeCoins2[] = {0,0,0,0,0,0};
        BigDecimal register = new BigDecimal(registerFloat);
        //not enough coins
        if(5 > register.floatValue() && balanceCoins.floatValue() >= register.floatValue()){
            Log.d("REG", "These coins");
            return changeCoins = algorithm(getCoins(), cValues, register.floatValue(), changeCoins, changeCoins2);
        }
        else if(balanceNotes.floatValue() >= register.floatValue()) {
            //ideal path
            Log.d("REG", "These notes");
            return changeNotes = algorithm(getNotes(), nValues, roundToFive(register.floatValue()), changeNotes, changeNotes2);

        }else if(balanceNotes.add(balanceCoins).floatValue() >= register.floatValue()){
            Log.d("REG", "All notes + these coins");
            float register2 = round(register.subtract(balanceNotes).floatValue(), 2);
            return changeCoins = algorithm(getCoins(), cValues, register2, changeCoins, changeCoins2);
        }
        else{
            Log.d("REG", "Not enough money");
            return changeNotes;
        }
    }

    public int[] algorithm(int[] wallet, float[] values, float register, int[] change1, int[] change2){
        float regTemp = register;//rounded up in 5's for notes(5 being lowest) eg. 23.50 = 25
        int i = 0;
        int j = 0;
        int x = 0;

        while (regTemp > 0) {
            if (regTemp >= values[i] && wallet[i] != 0) {
                regTemp -= values[i];
                wallet[i]--;
                change1[i]++;
                Log.d("REG", String.valueOf(regTemp));
            }
            else{
                i++;
            }

            if(i==wallet.length){//non ideal eg. has 50 note only but needs 25
                regTemp = register;
                Arrays.fill(change1, 0);
                if(wallet.length == 4) {
                    wallet = getNotes();
                }else {
                    wallet = getCoins();
                }
                i = 0;

                while (regTemp > 0){
                    if ( wallet[i] != 0) {
                        regTemp -= values[i];
                        wallet[i]--;
                        change1[i]++;
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
        wallet = getNotes();
        while(regTemp > 0){
            i=0;
            j=0;
            while(i != wallet.length){
                if(wallet[i] != 0 ){
                    x=i;
                    if(regTemp < values[j] && regTemp > values[i]) {
                        j=i;
                    }
                }
                else if(values[x] > regTemp){
                    j=x;
                }
                i++;
            }

            regTemp -= values[j];
            wallet[j]--;
            change2[j]++;
        }

            /*Log.d("REG", String.valueOf(changeNotes2[0]));
            Log.d("REG", String.valueOf(changeNotes2[1]));
            Log.d("REG", String.valueOf(changeNotes2[2]));
            Log.d("REG", String.valueOf(changeNotes2[3]));*/
        return change1 = compareChange(register, change1, change2, values);
    }

    //takes 2 change arrays to compare + cleans up 2nd after 2nd algorithm. register for coin compare needs to specific to coins
    public int[] compareChange(float register, int change[], int change2[], float values[]) {
        float changeTotal = 0;
        float changeTotal2 = 0;

        for(int i = 0; i < values.length; i++){
            changeTotal += change[i] * values[i];
            changeTotal2 += change2[i] * values[i];
        }

        float overflow = changeTotal - register;
        float overflow2 = changeTotal2 - register;
        int i = 0;

        //Clean up 2nd change array, algorithm for 2nd sometimes is better but has extra unneeded notes
        while(i < change2.length) {
            if (values[i] < overflow2 && change2[i] > 0) {
                overflow2 -= values[i];
                change2[i]--;
            } else{
                i++;
            }
        }
        changeTotal2 = change2[0] * values[0] + change2[1] * values[1] + change2[2] * values[2] + change2[3] * values[3];

        //check cleanup happened correctly, so enough change is given
        if(changeTotal2 < register){ Log.d("REG", "branch 1");
            return change;
        } else if(overflow > overflow2) { Log.d("REG", "branch 2");
            return change2;
        } else if(overflow < overflow2) { Log.d("REG", "branch 1");
            return change;
        } else if(overflow == overflow2) {
            int total = 0;
            int total2 = 0;

            for(i = 0; i < change.length; i++){
                total += change[i];
                total2 += change2[i];
            }

            if(total >= total2){ Log.d("REG", "branch 2"); return change2; }
            else{ Log.d("REG", "branch 1"); return change; }
        }

        return change;
    }

    public float roundToFive(float register){
        float temp = register;
        register = 5 *(Math.round(register/5));
        if(temp >= register){
            register += 5;
        }

        Log.d("REG", String.valueOf(register));
        return register;
    }

    public static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.floatValue();
    }

    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}
}