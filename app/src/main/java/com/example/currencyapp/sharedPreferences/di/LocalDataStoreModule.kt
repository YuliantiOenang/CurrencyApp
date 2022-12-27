package com.example.currencyapp.sharedPreferences.di

import com.example.currencyapp.sharedPreferences.CurrencyExchangeLocalDataStore
import com.example.currencyapp.sharedPreferences.SharedPreferenceLocalDataStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DLocalDataStoreModule {
    @Binds
    fun bindCurrencyExchangeLocalDataStore(sharedPreferenceLocalDataStore: SharedPreferenceLocalDataStore): CurrencyExchangeLocalDataStore
}