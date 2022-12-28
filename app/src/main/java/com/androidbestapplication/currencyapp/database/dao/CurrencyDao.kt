package com.androidbestapplication.currencyapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.androidbestapplication.currencyapp.database.entity.Currency
import io.reactivex.Observable

@Dao
interface CurrencyDao  {
    @Insert(onConflict = REPLACE)
    fun insertAll(currency: List<Currency>)

    @Query("SELECT * FROM currency")
    fun getAll(): List<Currency>

    @Query("SELECT * FROM currency")
    fun getAllObservable(): Observable<List<Currency>>

    @Query(value = "DELETE FROM currency WHERE currency_code in (:currency)")
    fun delete(currency: List<String>)
}