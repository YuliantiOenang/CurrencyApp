package com.androidbestapplication.currencyapp.database.dao

import androidx.room.*
import com.androidbestapplication.currencyapp.database.entity.ExchangeRate
import io.reactivex.Observable

@Dao
interface ExchangeRateDao  {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(exchangeRate: List<ExchangeRate>)

    @Query("SELECT * FROM exchange_rate")
    fun getAll(): List<ExchangeRate>

    @Query("SELECT * FROM exchange_rate")
    fun getAllObservable(): Observable<List<ExchangeRate>>

    @Query("SELECT * FROM exchange_rate WHERE to_currency = :to AND from_currency = :from")
    fun getExchangeRate(from: String, to: String): Observable<ExchangeRate>

    @Delete
    fun deleteById(list: List<ExchangeRate>)
}