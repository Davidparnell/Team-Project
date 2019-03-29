package com.moneyapp.Database;

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
    private String date;

	@NonNull
    @ColumnInfo(name = "balance")
    private float balance;
	
	@ColumnInfo(name = "register")
    private int register;
	
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
	
	@ColumnInfo(name = "location")
    private String location;
	
	@ColumnInfo(name = "receipt")
    private String receipt;

	//General Setters
    public void setWalletOptions(String date, float balance, int register, String location, String receipt)
    {this.date = date; this.balance = balance; this.register = register;  this.location = location; this.receipt =  receipt; };

    public void setNotes(int note50, int note20, int note10, int note5)
    {this.note50 = note50; this.note20 = note20; this.note10 = note10; this.note5 = note5;}

    public void setCoins(int coin2e, int coin1e, int coin50c, int coin20c, int coin10c, int coin5c)
    {this.coin2e = coin2e; this.coin1e = coin1e; this.coin50c = coin50c; this.coin20c = coin20c; this.coin10c = coin10c; this.coin5c = coin5c;}

    public int[] getNotes(){ return new int[]{note50, note20, note10, note5}; }

    public int[] getCoins(){ return new int[]{coin2e, coin1e, coin50c, coin20c, coin10c, coin5c}; }

    //-------Getters and Setters----------
    @NonNull
    public String getDate() {return date;}

    public void setDate(@NonNull String date){this.date = date;}

    public float getBalance() {return balance;}

    public void setBalance(float balance) {this.balance = balance;}

    public int getRegister() {return register;}

    public void setRegister(int register) {this.register = register;}

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

	public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}
	
	public String getReceipt() {return receipt;}

    public void setReceipt(String receipt) {this.receipt = receipt;}

    public String toString(){return date+" "+balance+" "+location+" "+register+" "+receipt+" "+note5+"\n";}
}
