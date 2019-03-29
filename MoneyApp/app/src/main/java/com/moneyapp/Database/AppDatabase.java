package com.moneyapp.Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {WalletData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WalletDAO getWalletDAO();
}
