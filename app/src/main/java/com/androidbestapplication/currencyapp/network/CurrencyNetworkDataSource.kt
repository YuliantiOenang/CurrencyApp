package com.androidbestapplication.currencyapp.network

import com.androidbestapplication.currencyapp.network.model.CurrencyExchangeRateModel
import com.google.gson.JsonObject
import io.reactivex.Observable

interface CurrencyNetworkDataSource {
    fun getAllCurrencyList(): Observable<JsonObject>
    fun getConvertRate(): Observable<CurrencyExchangeRateModel>
}