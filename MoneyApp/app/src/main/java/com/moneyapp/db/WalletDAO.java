package com.moneyapp.db;

import java.lang.reflect.Array;
import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WalletDAO {
    @Query("SELECT date, balance, note50, note20, note10, note5, location, receipt FROM wallet")
    List<String> getWallet();

    @Query("SELECT * FROM wallet WHERE date = :date")
    Wallet getTextByName(Date date);

    @Insert
    void insert(Wallet wallet);

    @Delete
    void delete(Wallet wallet);

    @Update
    void update(Wallet wallet);
}
