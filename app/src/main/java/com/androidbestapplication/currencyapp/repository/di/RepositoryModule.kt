package com.androidbestapplication.currencyapp.repository.di

import com.androidbestapplication.currencyapp.repository.CurrencyExchangeRateRepository
import com.androidbestapplication.currencyapp.repository.CurrencyRepository
import com.androidbestapplication.currencyapp.repository.OfflineCurrencyExchangeRateRepository
import com.androidbestapplication.currencyapp.repository.OfflineCurrencyRepository
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