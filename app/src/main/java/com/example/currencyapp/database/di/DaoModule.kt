package com.example.currencyapp.database.di

import com.example.currencyapp.database.dao.CurrencyDao
import com.example.currencyapp.database.dao.ExchangeRateDao
import com.example.currencyapp.database.databases.CurrencyDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {
    @Provides
    fun provideCurrencyDao(currencyDatabase: CurrencyDatabase) : CurrencyDao {
        return currencyDatabase.currencyDao()
    }

    @Provides
    fun provideExchangeRateDao(currencyDatabase: CurrencyDatabase): ExchangeRateDao {
        return currencyDatabase.exchangeRateDao()
    }
}