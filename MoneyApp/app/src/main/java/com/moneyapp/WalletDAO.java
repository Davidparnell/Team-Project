package com.moneyapp

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.moneyapp.Wallet;

import java.lang.reflect.Array;
import java.util.List;

@Dao
public interface WalletDAO {
    @Query("SELECT wallet, balance, note50, note20, note10, note5, location, receipt FROM wallet")
    List<String> getWallet();

    @Query("SELECT * FROM note WHERE name = :name")
    Note getTextByName(String name);

    @Insert
    void insert(Wallet wallet);

    @Delete
    void delete(Wallet wallet);

    @Update
    void update(Wallet wallet);
}
