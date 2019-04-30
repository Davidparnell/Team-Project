package com.moneyapp.database;

import java.util.Date;
import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

/*
Converting SQL to to java allowing to query the database
 */

@Dao
public interface WalletDAO {
    @Query("SELECT * FROM wallet Order BY date DESC")
    List<WalletData> getWalletHistory();

    @Query("SELECT * FROM wallet ORDER BY date DESC LIMIT 1")
    WalletData getRecentWallet();

    @Query("DELETE FROM wallet WHERE date <= :from")
    void deleteOlderThenMonth(Date from);

    @Insert
    void insert(WalletData walletData);

    @Delete
    void delete(WalletData walletData);

    @Update
    void update(WalletData walletData);
}