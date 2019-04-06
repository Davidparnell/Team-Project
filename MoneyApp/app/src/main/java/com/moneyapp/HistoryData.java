package com.moneyapp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/*
    Class used to contain data for the History listview.
 */
public class HistoryData
{
    private String date;
    private String balance;
    private String register;
    private Drawable type;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public Drawable getType() {
        return type;
    }

    public void setType(Drawable type) {
        this.type = type;
    }
}
