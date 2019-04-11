package com.moneyapp.database;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wallet")
public class WalletData {
    //--------Attributes---------
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "date")
    private Date date;

	@NonNull
    @ColumnInfo(name = "balance")
    private float balance;
	
	@ColumnInfo(name = "register")
    private float register;
	
	@ColumnInfo(name = "note50")
    private int note50;
	
	@ColumnInfo(name = "note20")
    private int note20;
	
	@ColumnInfo(name = "note10")
    private int note10;
	
	@ColumnInfo(name = "note5")
    private int note5;

    @ColumnInfo(name = "coin2e")
    private int coin2e;

    @ColumnInfo(name = "coin1e")
    private int coin1e;

    @ColumnInfo(name = "coin50c")
    private int coin50c;

    @ColumnInfo(name = "coin20c")
    private int coin20c;

    @ColumnInfo(name = "coin10c")
    private int coin10c;

    @ColumnInfo(name = "coin5c")
    private int coin5c;

    //General Setters
    public void setWalletOptions(Date date, float balance, float register)
    {this.date = date; this.balance = balance; this.register = register;};

    public void setNotes(int[] notes)
    {this.note50 = notes[0]; this.note20 = notes[1]; this.note10 = notes[2]; this.note5 = notes[3];}

    public void setCoins(int[] coins)
    {this.coin2e = coins[0]; this.coin1e = coins[1]; this.coin50c = coins[2]; this.coin20c = coins[3]; this.coin10c = coins[4]; this.coin5c = coins[5];}

    public int[] getNotes(){ return new int[]{note50, note20, note10, note5}; }

    public int[] getCoins(){ return new int[]{coin2e, coin1e, coin50c, coin20c, coin10c, coin5c}; }

    //-------Getters and Setters----------
    @NonNull
    public Date getDate() {return date;}

    public void setDate(@NonNull Date date){this.date = date;}

    public float getBalance() {return balance;}

    public void setBalance(float balance) {this.balance = balance;}

    public float getRegister() {return register;}

    public void setRegister(float register) {this.register = register;}

    public int getNote50() {return note50;}

    public void setNote50(int note50) {this.note50 = note50;}

    public int getNote20() {return note20;}

    public void setNote20(int note20) {this.note20 = note20;}

    public int getNote10() {return note10;}

    public void setNote10(int note10) {this.note10 = note10;}

    public int getNote5() {return note5;}

    public void setNote5(int note5) {this.note5 = note5;}

    public int getCoin2e() {return coin2e;}

    public void setCoin2e(int coin2e) {this.coin2e = coin2e;}

    public int getCoin1e() {return coin1e;}

    public void setCoin1e(int coin1e) {this.coin1e = coin1e;}

    public int getCoin50c() {return coin50c;}

    public void setCoin50c(int coin50c) {this.coin50c = coin50c;}

    public int getCoin20c() {return coin20c;}

    public void setCoin20c(int coin20c) {this.coin20c = coin20c;}

    public int getCoin10c() {return coin10c;}

    public void setCoin10c(int coin10c) {this.coin10c = coin10c;}

    public int getCoin5c() {return coin5c;}

    public void setCoin5c(int coin5c) {this.coin5c = coin5c;}

    public String toString(){return date+" "+balance+" "+register+"\n";}
}
