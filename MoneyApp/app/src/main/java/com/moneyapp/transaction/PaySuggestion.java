package com.moneyapp.transaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

/*
    Generates a suggestion of notes and coins to be given based on register value read by the camera.
    There are 2 algorithms that generate 2 arrays of notes/coins out of which the best is chosen.
    What is passed to the algorithm depends on 4 paths;
        below 5 = coins, above 5 = notes, amount needed > then notes total but less the full total = all notes and remainder coins, and not enough money had.
    It then adds changes to database, if change given, database entry is updated in next activity.
*/

public class PaySuggestion extends AppCompatActivity {
    int notes[];
    int coins[];
    int path = 0;

    private ListView listView;
    private List<SuggestionData> suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        listView = findViewById(R.id.suggestionList);
        suggestionList = new ArrayList<>();
        
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());

        WalletDAO walletDAO = database.getWalletDAO();

        WalletData walletData = walletDAO.getRecentWallet();
        Intent intent = getIntent();
        float register = Float.parseFloat(intent.getStringExtra("register"));
        notes = walletData.getNotes();
        coins = walletData.getCoins();

        int pay[] = generateSuggestion(register);
        for(int i = 0; i< pay.length; i++) {
            Log.d("REG", "["+String.valueOf(i)+"] - "+String.valueOf(pay[i]));
        }
        //from here -------------------------
        int payNotes[] = {0,0,0,0};
        int payCoins[] = {0,0,0,0,0,0};
        if(path == 1) {
            payCoins = pay;
        }
        else if(path == 2){
            payNotes = pay;
        }else if(path == 3){
            payCoins = pay;
            payNotes = notes;
        }else if( path == 4){
            //nothing not enough money
        }//-----------------------------------

        ArrayList<String> cash = new ArrayList<String>();
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};

        for(int i = 0; i < payNotes.length; i++){
            for(int j = 0; j < payNotes[i]; j++){
                cash.add(String.valueOf(nValues[i]));
            }
        }

        for(int i = 0; i < payCoins.length; i++){
            for(int j = 0; j < payCoins[i]; j++){
                cash.add(String.valueOf(cValues[i]));
            }
        }

        for (int i = 0; i < cash.size(); i++)
        {
            SuggestionData item = new SuggestionData();

            if (cash.get(i).equals("50.0"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.fifty_euro);
                item.setCash(drawable);
            }
            else if (cash.get(i).equals("20.0"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.twenty_euro);
                item.setCash(drawable);
            }
            else if (cash.get(i).equals("10.0"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.ten_euro);
                item.setCash(drawable);
            }
            else if (cash.get(i).equals("5.0"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.five_euro);
                item.setCash(drawable);
            }
            else if (cash.get(i).equals("2.0"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.two_euro);
                item.setCash(drawable);
            }
            else if (cash.get(i).equals("1.0"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.one_euro);
                item.setCash(drawable);
            }
            else if (cash.get(i).equals("0.5"))
            {
                Drawable drawable = getResources().getDrawable(R.drawable.fifty_cent);
                item.setCash(drawable);
            }
            suggestionList.add(item);
        }

        Log.d("REG", cash.toString());

        //suggestionList.add(new SuggestionData);

        walletData = updateWallet(register, pay, walletData);
        //Log.d("REG", walletData.toString());

        SuggestionAdapter adapter = new SuggestionAdapter(suggestionList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    //Path for what needs to be generated using algorithm
    public int[] generateSuggestion(float registerFloat){
        float nValues[] = {50f, 20f, 10f, 5f};
        float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};
        BigDecimal balanceNotes = BigDecimal.valueOf(notes[0] * nValues[0] + notes[1] * nValues[1] + notes[2] * nValues[2] + notes[3] * nValues[3]);
        BigDecimal balanceCoins = BigDecimal.valueOf(coins[0] * cValues[0] + coins[1] + coins[2] * cValues[2] + coins[3] * cValues[3] + coins[4] * cValues[4] + coins[5] * cValues[5]);
        int payNotes[] = {0,0,0,0};
        int payNotes2[] = {0,0,0,0};
        int payCoins[] = {0,0,0,0,0,0};
        int payCoins2[] = {0,0,0,0,0,0};
        BigDecimal register = new BigDecimal(registerFloat);

        if(5 > register.floatValue() && balanceCoins.floatValue() >= register.floatValue()){       //Small amounts below 5 if enough coins in wallet
            Log.d("REG", "These coins");
            setPath(1);
            return payCoins = algorithm(getCoins(), cValues, register.floatValue(), payCoins, payCoins2);
        }
        else if(balanceNotes.floatValue() >= register.floatValue()) {                              //amounts bigger then 5 euro or less then 5 if not enough coins
            Log.d("REG", "These notes");
            setPath(2);
            return payNotes = algorithm(getNotes(), nValues, roundToFive(register.floatValue()), payNotes, payNotes2);
        }
        else if(balanceNotes.add(balanceCoins).floatValue() >= register.floatValue()){             //amounts bigger then total in notes but less
            Log.d("REG", "All notes + these coins");                                     //then total in wallet - all notes and coins algorithm for remainder
            setPath(3);
            float register2 = register.subtract(balanceNotes).floatValue();
            return payCoins = algorithm(getCoins(), cValues, register2, payCoins, payCoins2);
        }
        else{
            Log.d("REG", "Not enough money");                                            //Not enough money
            setPath(4);
            return payNotes;
        }
    }

    //Generates 2 arrays with 2 different solutions
    public int[] algorithm(int[] wallet, float[] values, float register, int[] pay1, int[] pay2){
        float regTemp = register;//rounded up in 5's for notes(5 being lowest) eg. 23.50 = 25
        int i = 0;
        int j = 0;
        int x = 0;
        //first algorithm iteration - generates first money array
        while (regTemp > 0) {
            if (regTemp >= values[i] && wallet[i] != 0) {
                regTemp -= values[i];
                wallet[i]--;
                pay1[i]++;
            }
            else{
                i++;
            }

            if(i==wallet.length){//non ideal eg. has 50 note only but needs 25
                regTemp = register;  //reset variables
                Arrays.fill(pay1, 0);
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
                        pay1[i]++;
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

        //Second change algorithm variation - generates 2nd money array
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
            pay2[j]++;
        }
        //compare which is better after cleaning up the second which sometimes ends with redundant notes/coins
        return comparePayment(register, pay1, pay2, values);
    }

    //takes 2 pay arrays to compare + cleans up 2nd after 2nd algorithm. register for coin compare needs to specific to coins
    public int[] comparePayment(float register, int pay[], int pay2[], float values[]) {
        float payTotal = 0;
        float payTotal2 = 0;

        for(int i = 0; i < values.length; i++){
            payTotal += pay[i] * values[i];
            payTotal2 += pay2[i] * values[i];
        }

        float overflow = payTotal - register;  //Which is closer to amount needed
        float overflow2 = payTotal2 - register;
        int i = 0;

        //Clean up 2nd pay array, algorithm for 2nd sometimes is better but has extra unneeded notes
        while(i < pay2.length) {
            if (values[i] < overflow2 && pay2[i] > 0) {
                overflow2 -= values[i];
                pay2[i]--;
            } else{
                i++;
            }
        }
        payTotal2 = pay2[0] * values[0] + pay2[1] * values[1] + pay2[2] * values[2] + pay2[3] * values[3];

        //check cleanup happened correctly, so enough pay is given
        if(payTotal2 < register){ Log.d("REG", "branch 1");
            return pay;
        } else if(overflow > overflow2) { Log.d("REG", "branch 2");   //pay more accurate
            return pay2;
        } else if(overflow < overflow2) { Log.d("REG", "branch 1");   //pay2 more accurate
            return pay;
        } else if(overflow == overflow2) {                                      //equal but one uses more notes/coins to get same amount
            int total = 0;
            int total2 = 0;

            for(i = 0; i < pay.length; i++){
                total += pay[i];
                total2 += pay2[i];
            }

            if(total >= total2){ Log.d("REG", "branch 2"); return pay2; }  //pay2 used less notes/coins
            else{ Log.d("REG", "branch 1"); return pay; }                  //pay used less notes/coins
        }
        return null;
    }

    //Rounds numbers to nearest 5 eg. 23.5 = 25, 0.2 = 5
    public float roundToFive(float register){
        float temp = register;
        register = 5 *(Math.round(register/5));
        if(temp >= register){
            register += 5;
        }
        return register;
    }

    //Makes an object after suggested notes are assumed to be given, and adds it to database
    public WalletData updateWallet(float register, int[] pay, WalletData walletData){
        Date date = Calendar.getInstance().getTime();
        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        WalletDAO walletDAO = database.getWalletDAO();

        int[] notes = walletData.getNotes();
        int[] coins = walletData.getCoins();
        if(path == 1){
            for(int i = 0; i< coins.length; i++){
                coins[i] -= pay[i];
            }
        }
        else if(path == 2){
            for(int i = 0; i< notes.length; i++){
                notes[i] -= pay[i];
            }
        }
        else if(path == 3){
            for(int i = 0; i< coins.length; i++){
                coins[i] -= pay[i];
            }
            Arrays.fill(notes, 0);
        }
        else if(path == 4){
            return null;
        }

        float total = notes[0] * 50f + notes[1] * 20f + notes[2] * 10f + notes[3] * 5f;
        total += coins[0] * 2.00f + coins[1] * 1.00f + coins[2] * 0.50f + coins[3] * 0.20f + coins[4] * 0.10f + coins[5] * 0.05f;

        walletData.setWalletOptions(date, total, register);
        walletData.setNotes(notes);
        walletData.setCoins(coins);
        walletDAO.insert(walletData);

        return walletData;
    }


    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}

    public void setPath(int path) { this.path = path; }
}