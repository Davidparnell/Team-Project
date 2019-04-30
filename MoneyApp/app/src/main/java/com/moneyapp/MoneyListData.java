package com.moneyapp;

import android.graphics.drawable.Drawable;

/*
Stores data for the edit and suggestion lists
 */

public class MoneyListData
{
    private Drawable cash;
    private String type; //type and index for referencing this object when deleted in edit wallet
    private int index;

    public Drawable getCash() {
        return cash;
    }

    public void setCash(Drawable cash) {
        this.cash = cash;
    }

    public String getType() { return type; }

    public void setType(String type) { this.type = type; }

    public int getIndex() { return index; }

    public void setIndex(int index) { this.index = index; }
}
