package com.moneyapp.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Wallet.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WalletDAO getWalletDAO();
}
