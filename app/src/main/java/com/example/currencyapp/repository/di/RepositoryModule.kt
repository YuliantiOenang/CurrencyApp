package com.example.currencyapp.repository.di

import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.repository.OfflineCurrencyExchangeRateRepository
import com.example.currencyapp.repository.OfflineCurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    fun bindCurrencyRepository(currencyRepository: OfflineCurrencyRepository): CurrencyRepository

    @Binds
    fun bindCurrencyExchangeRateRepository(exchangeRateRepository: OfflineCurrencyExchangeRateRepository): CurrencyExchangeRateRepository
}