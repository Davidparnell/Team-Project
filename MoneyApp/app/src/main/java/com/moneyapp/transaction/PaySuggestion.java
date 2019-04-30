package com.moneyapp.transaction;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.moneyapp.MoneyListAdapter;
import com.moneyapp.MoneyListData;
import com.moneyapp.database.AppDatabase;
import com.moneyapp.database.WalletDAO;
import com.moneyapp.database.WalletData;
import com.moneyapp.R;
import com.moneyapp.wallet.Wallet;

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
    int[] notes;
    int[] coins;
    int path = 0;
    BigDecimal change = new BigDecimal(0).setScale(3, BigDecimal.ROUND_HALF_UP);
    final float[] nValues = {50f, 20f, 10f, 5f};
    final float[] cValues = {2f, 1f, 0.50f, 0.20f, 0.10f, 0.05f};

    private List<MoneyListData> suggestionList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggestion);
        ListView listView = findViewById(R.id.suggestionList);
        ImageView noMoney = findViewById(R.id.no_money);

        //Floating button used to go to next activity.
        FloatingActionButton floating_btn = findViewById(R.id.floating_tick);
        floating_btn.setOnClickListener(this);

        AppDatabase database = AppDatabase.getDatabase(getApplicationContext());
        WalletDAO walletDAO = database.getWalletDAO();

        //get current wallet and register reading from camera
        WalletData walletData = walletDAO.getRecentWallet();
        Intent intent = getIntent();
        float register = Float.parseFloat(intent.getStringExtra("register"));
        notes = walletData.getNotes();
        coins = walletData.getCoins();

        int[] pay = generateSuggestion(register);

        //Calculate change to know if wallet is necessary
        change = BigDecimal.valueOf(register);
        for (int i = 0; i < pay.length; i++) {
            if(path == 1 || path == 3){
                change = change.subtract(BigDecimal.valueOf(cValues[i] * pay[i]));
            }else if(path == 2){
                change = change.subtract(BigDecimal.valueOf(nValues[i] * pay[i]));
            }else{
                change = BigDecimal.ZERO;
            }
        }

        if(path == 4){  //if not enough money
            noMoney.setVisibility(View.VISIBLE);
        }
        else{
            suggestionList = createSuggestionList(pay);
            walletData = updateWallet(register, pay, walletData);

            MoneyListAdapter adapter = new MoneyListAdapter(suggestionList, getApplicationContext());
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

    public List<MoneyListData> createSuggestionList(int[] pay){
        int[] payNotes = {0,0,0,0};
        int[] payCoins = {0,0,0,0,0,0};

        //Convert suggested notes/coins to into arrays dependant on algorithm path
        if(path == 1) {
            payCoins = pay;
        } else if(path == 2){
            payNotes = pay;
        }else if(path == 3){
            payCoins = pay;
            payNotes = notes;
        }

        ArrayList<String> cash = new ArrayList<String>();
        suggestionList = new ArrayList<>();//suggested notes/coins turned into into single string array

        cash.add("arrow");
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
                case "arrow":{
                    Drawable arrow = getResources().getDrawable(R.drawable.transaction_arrow);
                    item.setCash(arrow);
                    break;
                }
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
    public int[] generateSuggestion(float registerFloat){  //BigDecimal because floats are not accurate enough
        BigDecimal balanceNotes = BigDecimal.valueOf(notes[0] * nValues[0] + notes[1] * nValues[1] + notes[2] * nValues[2] + notes[3] * nValues[3]).setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal balanceCoins = BigDecimal.valueOf(coins[0] * cValues[0] + coins[1] + coins[2] * cValues[2] + coins[3] * cValues[3] + coins[4] * cValues[4] + coins[5] * cValues[5]).setScale(3, BigDecimal.ROUND_HALF_UP);
        BigDecimal register = new BigDecimal(registerFloat).setScale(3, BigDecimal.ROUND_HALF_UP);
        int[] payNotes = {0,0,0,0};
        int[] payNotes2 = {0,0,0,0};
        int[] payCoins = {0,0,0,0,0,0};
        int[] payCoins2 = {0,0,0,0,0,0};

        if(5 > register.floatValue() && balanceCoins.floatValue() >= register.floatValue()){       //Small amounts below 5 if enough coins in wallet
            setPath(1);
            return payCoins = algorithm(getCoins(), cValues, register, payCoins, payCoins2);
        }
        else if(balanceNotes.floatValue() >= register.floatValue()) {                              //amounts bigger then 5 euro or less then 5 if not enough coins
            setPath(2);
            return payNotes = algorithm(getNotes(), nValues, new BigDecimal(roundToFive(register.floatValue())), payNotes, payNotes2);
        }
        else if(balanceNotes.add(balanceCoins).floatValue() >= register.floatValue()){             //amounts bigger then total in notes but less
            setPath(3);                                                                            //then total in wallet - all notes and coins algorithm for remainder
            return payCoins = algorithm(getCoins(), cValues, register.subtract(balanceNotes), payCoins, payCoins2);
        }
        else{//Not enough money
            setPath(4);
            return payNotes;
        }
    }

    //Generates 2 arrays with 2 different solutions
    public int[] algorithm(int[] wallet, float[] values, BigDecimal register, int[] pay1, int[] pay2){
        BigDecimal regTemp = register.setScale(3, BigDecimal.ROUND_HALF_UP);//rounded up in 5's for notes(5 being lowest) eg. 23.50 = 25
        int i = 0;
        int iPrev = 0;
        int x = 0;

        //first algorithm iteration - generates first money array
        while (regTemp.floatValue() > 0) {
            if (regTemp.floatValue() >= values[i] && wallet[i] != 0) {
                regTemp = regTemp.subtract(BigDecimal.valueOf(values[i]).setScale(3, BigDecimal.ROUND_HALF_UP));
                wallet[i]--;
                pay1[i]++;
            }
            else{
                i++;
            }

            if(i == wallet.length){//non ideal eg. has 50 note only but needs 25
                regTemp = register;  //reset variables
                Arrays.fill(pay1, 0);
                if(wallet.length == 4) {
                    wallet = getNotes();
                }else {
                    wallet = getCoins();
                }
                i = 0;

                while (regTemp.floatValue() > 0){

                    if ( wallet[i] != 0) {
                        regTemp = regTemp.subtract(BigDecimal.valueOf(values[i]).setScale(3, BigDecimal.ROUND_HALF_UP));
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
        regTemp = register;
        if(wallet.length == 4) {//reset notes if note length
            wallet = getNotes();
        }else {                 //reset coins if coin length
            wallet = getCoins();
        }

        while(regTemp.floatValue() > 0){ //until all register accounted for
            i=0;
            iPrev=0;
            while(i != wallet.length){
                if(wallet[i] != 0){
                    x=i;
                    if(regTemp.floatValue() <= values[iPrev] || regTemp.floatValue() > values[i]) {
                        iPrev=i;
                    }
                }
                else if(values[x] > regTemp.floatValue()){
                    iPrev=x;
                }
                i++;
            }

            regTemp = regTemp.subtract(BigDecimal.valueOf(values[iPrev]).setScale(3, BigDecimal.ROUND_HALF_UP));
            wallet[iPrev]--;
            pay2[iPrev]++;

            for(i = 0; i < pay2.length; i++){ //check negative numbers of notes/coins in case algorithm messed up
                if(pay2[i] < 0){
                    pay2 = pay1;
                }
            }
        }
        //compare which is better after cleaning up the second which sometimes ends with redundant notes/coins
        return comparePayment(register.floatValue(), pay1, pay2, values);
    }

    //takes 2 pay arrays to compare + cleans up 2nd after 2nd algorithm. register for coin compare needs to specific to coins
    public int[] comparePayment(float register, int[] pay1, int[] pay2, float[] values) { //values are note/coin numbers eg. 50,20,10,5,2,1,0.5 etc.
        float payTotal = 0;
        float payTotal2 = 0;

        for(int i = 0; i < values.length; i++){
            payTotal += pay1[i] * values[i];
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
            return pay1;
        } else if(overflow > overflow2) { Log.d("REG", "branch 2");   //pay more accurate
            return pay2;
        } else if(overflow < overflow2) { Log.d("REG", "branch 1");   //pay2 more accurate
            return pay1;
        } else if(overflow == overflow2) {                                      //equal but one uses more notes/coins to get same amount
            int total = 0;
            int total2 = 0;

            for(i = 0; i < pay1.length; i++){
                total += pay1[i];
                total2 += pay2[i];
            }

            if(total >= total2){ Log.d("REG", "branch 2"); return pay2; }  //pay2 used less notes/coins
            else{ Log.d("REG", "branch 1"); return pay1; }                  //pay used less notes/coins
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
        if(path == 1){ //wallet - suggested notes
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

        //balance after suggestion balance
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
        //Insert code to proceed to next activity here.
        if (v.getId() == R.id.floating_tick) {

            if(change.compareTo(BigDecimal.ZERO) != 0){
                //Insert code to proceed to next activity here.
                Intent intent = new Intent(getApplicationContext(), Wallet.class);//getIntent so that register value passed from camera is still inside
                startActivity(intent);
            }else{
                finish();
            }
        }
    }

    public int[] getNotes(){return new int[] {notes[0], notes[1], notes[2], notes[3]};}

    public int[] getCoins(){return new int[] {coins[0], coins[1], coins[2], coins[3], coins[4], coins[5]};}

    public void setPath(int path) { this.path = path; }
}
