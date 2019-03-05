package com.example.mapapp2.dmmtmoneyapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.example.project.project.model.Note;

@Database(entities = {Wallet.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WalletDAO getWalletDAO();
}
