package com.moneyapp.db;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface WalletDAO {
    @Query("SELECT * FROM wallet Order BY date DESC")
    List<Wallet> getWalletHistory();

    @Query("SELECT * FROM wallet ORDER BY date DESC LIMIT 1")
    Wallet getRecentWallet();

    @Query("DELETE FROM wallet")
    void deleteAll();

    @Insert
    void insert(Wallet wallet);

    @Delete
    void delete(Wallet wallet);

    @Update
    void update(Wallet wallet);
}