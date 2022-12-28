package com.androidbestapplication.currencyapp.repository

import com.androidbestapplication.currencyapp.database.entity.Currency
import io.reactivex.Observable

interface CurrencyRepository {
    fun getCurrencyList(): Observable<List<Currency>>
}