package com.androidbestapplication.currencyapp.network.di

import com.androidbestapplication.currencyapp.network.CurrencyNetworkDataSource
import com.androidbestapplication.currencyapp.network.retrofit.RetrofitCurrencyNetwork
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