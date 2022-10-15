package com.example.currencyapp.network.di

import com.example.currencyapp.network.CurrencyNetworkDataSource
import com.example.currencyapp.network.retrofit.RetrofitCurrencyNetwork
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {
    @Binds
    fun bindCurrencyNetwork(retrofitCurrencyNetwork: RetrofitCurrencyNetwork): CurrencyNetworkDataSource
}