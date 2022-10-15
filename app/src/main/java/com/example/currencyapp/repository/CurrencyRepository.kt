package com.example.currencyapp.repository

import com.example.currencyapp.database.entity.Currency
import io.reactivex.Observable

interface CurrencyRepository {
    fun getCurrencyList(): Observable<List<Currency>>
}