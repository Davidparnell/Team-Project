package com.moneyapp.db;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "wallet")
public class Wallet {//--------attributes---------
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
	
	@ColumnInfo(name = "location")
    private String location;
	
	@ColumnInfo(name = "receipt")
    private String receipt;

    public Wallet() {
    }

    //-------functions----------
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

    public void setNote5(int note5) {this.note50 = note5;}

	public String getLocation() {return location;}

    public void setLocation(String location) {this.location = location;}
	
	public String getReceipt() {return receipt;}

    public void setReceipt(String receipt) {this.receipt = receipt;}
	
    public String toString(){return date+" "+balance+" "+location+" "+register+" "+receipt+" "+note5;}
}
