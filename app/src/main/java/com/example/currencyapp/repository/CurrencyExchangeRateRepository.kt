package com.example.currencyapp.repository

import com.example.currencyapp.database.entity.ExchangeRate
import io.reactivex.Observable

interface CurrencyExchangeRateRepository {
    fun getExchangeRate(): Observable<List<ExchangeRate>>
    fun getExchangeRate(from: String, to: String): Observable<ExchangeRate>
}