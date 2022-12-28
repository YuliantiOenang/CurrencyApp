package com.androidbestapplication.currencyapp.database.databases

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidbestapplication.currencyapp.database.dao.CurrencyDao
import com.androidbestapplication.currencyapp.database.dao.ExchangeRateDao
import com.androidbestapplication.currencyapp.database.entity.Currency
import com.androidbestapplication.currencyapp.database.entity.ExchangeRate

@Database(entities = [Currency::class, ExchangeRate::class], version = 2)
abstract class CurrencyDatabase: RoomDatabase() {
    abstract fun currencyDao(): CurrencyDao
    abstract fun exchangeRateDao(): ExchangeRateDao
}