package com.moneyapp.transaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moneyapp.MoneyListAdapter;
import com.moneyapp.MoneyListData;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;

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

public class PaySuggestion extends AppCompatActivity implements View.OnClickListener
{
    int notes[];
    int coins[];
    int path = 0;
    final float nValues[] = {50f, 20f, 10f, 5f};
    final float cValues[] = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};

    private ListView listView;
    private List<MoneyListData> suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        listView = findViewById(R.id.suggestionList);

        //Floating button used to go to next activity.
        FloatingActionButton floating_btn = findViewById(R.id.floating_tick);
        floating_btn.setOnClickListener(this);

        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        WalletDAO walletDAO = database.getWalletDAO();

        WalletData walletData = walletDAO.getRecentWallet();
        Intent intent = getIntent();
        float register = Float.parseFloat(intent.getStringExtra("register"));
        notes = walletData.getNotes();
        coins = walletData.getCoins();

        Log.d("WALLET","generate");
        int pay[] = generateSuggestion(register);
        /*for(int i = 0; i< pay.length; i++) {
            Log.d("REG", "["+String.valueOf(i)+"] - "+String.valueOf(pay[i]));
        }*/
        Log.d("WALLET","display");
        suggestionList = createSuggestionList(pay);

        walletData = updateWallet(register, pay, walletData);

        MoneyListAdapter adapter = new MoneyListAdapter(suggestionList, getApplicationContext());
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public List<MoneyListData> createSuggestionList(int[] pay){
        int payNotes[] = {0,0,0,0};
        int payCoins[] = {0,0,0,0,0,0};

        if(path == 1) {
            payCoins = pay;
        } else if(path == 2){
            payNotes = pay;
        }else if(path == 3){
            payCoins = pay;
            payNotes = notes;
        }

        ArrayList<String> cash = new ArrayList<String>();
        suggestionList = new ArrayList<>();

        for(int i = 0; i < payNotes.length; i++){
            for(int j = 0; j < payNotes[i]; j++){
                //Add note value as cash to the cash ArrayList
                cash.add(String.valueOf(nValues[i]));
            }
        }

        for(int i = 0; i < payCoins.length; i++){
            for(int j = 0; j < payCoins[i]; j++){
                //Add coin value as cash to the cash ArrayList
                cash.add(String.valueOf(cValues[i]));
            }
        }

        //Creating drawables from the cash ArrayList values.
        for (int i = 0; i < cash.size(); i++)
        {
            MoneyListData item = new MoneyListData();

            switch (cash.get(i)) {
                case "50.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.fifty_euro);
                    item.setCash(drawable);
                    break;
                }
                case "20.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.twenty_euro);
                    item.setCash(drawable);
                    break;
                }
                case "10.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.ten_euro);
                    item.setCash(drawable);
                    break;
                }
                case "5.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.five_euro);
                    item.setCash(drawable);
                    break;
                }
                case "2.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.two_euro);
                    item.setCash(drawable);
                    break;
                }
                case "1.0": {
                    Drawable drawable = getResources().getDrawable(R.drawable.one_euro);
                    item.setCash(drawable);
                    break;
                }
                case "0.5": {
                    Drawable drawable = getResources().getDrawable(R.drawable.fifty_cent);
                    item.setCash(drawable);
                    break;
                }
                case "0.2": {
                    Drawable drawable = getResources().getDrawable(R.drawable.twenty_cent);
                    item.setCash(drawable);
                    break;
                }
                case "0.1": {
                    Drawable drawable = getResources().getDrawable(R.drawable.ten_cent);
                    item.setCash(drawable);
                    break;
                }
                case "0.05": {
                    Drawable drawable = getResources().getDrawable(R.drawable.five_cent);
                    item.setCash(drawable);
                    break;
                }
            }
            //Insert calculated drawable into the suggestionList ArrayList.
            suggestionList.add(item);
        }
        return suggestionList;
    }

    //Path for what needs to be generated using algorithm
    public int[] generateSuggestion(float registerFloat){
        BigDecimal balanceNotes = BigDecimal.valueOf(notes[0] * nValues[0] + notes[1] * nValues[1] + notes[2] * nValues[2] + notes[3] * nValues[3]).setScale(2, BigDecimal.ROUND_HALF_UP);;
        BigDecimal balanceCoins = BigDecimal.valueOf(coins[0] * cValues[0] + coins[1] + coins[2] * cValues[2] + coins[3] * cValues[3] + coins[4] * cValues[4] + coins[5] * cValues[5]);
        int payNotes[] = {0,0,0,0};
        int payNotes2[] = {0,0,0,0};
        int payCoins[] = {0,0,0,0,0,0};
        int payCoins2[] = {0,0,0,0,0,0};
        BigDecimal register = new BigDecimal(registerFloat).setScale(2, BigDecimal.ROUND_HALF_EVEN);

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
            Log.d("WALLET", register.subtract(balanceNotes).toString());

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
    public int[] algorithm(int[] wallet, float[] value, float register, int[] pay1, int[] pay2){
        BigDecimal regTemp = new BigDecimal(register).setScale(3, BigDecimal.ROUND_HALF_UP);//rounded up in 5's for notes(5 being lowest) eg. 23.50 = 25
        //BigDecimal register = new BigDecimal(registe).setScale(2, BigDecimal.ROUND_HALF_EVEN);
        BigDecimal values[] = {new BigDecimal(2), new BigDecimal(1), new BigDecimal(0.50), new BigDecimal(0.20), new BigDecimal(0.10), new BigDecimal(0.05)};
        //float regTemp = register;
        int i = 0;
        int j = 0;
        int x = 0;
        //first algorithm iteration - generates first money array
        //Log.d("WALLET", register.toString());
        while (regTemp.floatValue() > 0) {
            if (regTemp.floatValue() >= values[i].floatValue() && wallet[i] != 0) {
                regTemp = regTemp.subtract(BigDecimal.valueOf(value[i]).setScale(3, BigDecimal.ROUND_HALF_UP));
                wallet[i]--;
                pay1[i]++;
                Log.d("WALLET",regTemp.toString());
            }
            else{
                i++;
            }

            if(i == wallet.length){//non ideal eg. has 50 note only but needs 25
                regTemp = BigDecimal.valueOf(register);  //reset variables
                Arrays.fill(pay1, 0);
                if(wallet.length == 4) {
                    wallet = getNotes();
                }else {
                    wallet = getCoins();
                }
                i = 0;
                Log.d("WALLET","2nd");
                while (regTemp.floatValue() > 0){

                    if ( wallet[i] != 0) {
                        regTemp = regTemp.subtract(values[i]);
                        wallet[i]--;
                        pay1[i]++;
                    }
                    else{

                        i++;
                        if(i < 0){
                            regTemp = BigDecimal.ZERO;
                        }
                    }
                }
            }
        }

        //Second change algorithm variation - generates 2nd money array
        regTemp = BigDecimal.valueOf(register);
        if(wallet.length == 4) {
            wallet = getNotes();
        }else {
            wallet = getCoins();
        }
        Log.d("WALLET","3rd");
        while(regTemp.floatValue() > 0){
            i=0;
            j=0;
            while(i != wallet.length){
                if(wallet[i] != 0 ){
                    x=i;
                    if(regTemp.floatValue() < values[j].floatValue() && regTemp.floatValue() > values[i].floatValue()) {
                        j=i;
                    }
                }
                else if(values[x].floatValue() > regTemp.floatValue()){
                    j=x;
                }
                i++;
            }

            regTemp = regTemp.subtract(values[j]);
            wallet[j]--;
            pay2[j]++;
        }
        Log.d("WALLET","after algorithm");
        //compare which is better after cleaning up the second which sometimes ends with redundant notes/coins
        return comparePayment(register, pay1, pay2, value);
    }

    //takes 2 pay arrays to compare + cleans up 2nd after 2nd algorithm. register for coin compare needs to specific to coins
    public int[] comparePayment(float register, int pay[], int pay2[], float values[]) { //values are note/coin numbers eg. 50,20,10,5,2,1,0.5 etc.
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
            if (values[i] <= overflow2 && pay2[i] > 0) {
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
        if(temp > register){
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

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.floating_tick:
            {
                //Insert code to proceed to next activity here.
                Toast.makeText(getApplicationContext(), "Go to change", Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}

    public void setPath(int path) { this.path = path; }
}
