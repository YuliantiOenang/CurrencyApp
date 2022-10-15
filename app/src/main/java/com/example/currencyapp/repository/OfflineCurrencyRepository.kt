package com.example.currencyapp.repository

import com.example.currencyapp.database.dao.CurrencyDao
import com.example.currencyapp.database.entity.Currency
import com.example.currencyapp.network.CurrencyNetworkDataSource
import com.example.currencyapp.sharedPreferences.CurrencyExchangeLocalDataStore
import com.example.currencyapp.sharedPreferences.LocalDataStoreConstant
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OfflineCurrencyRepository @Inject constructor(
    private val networkDataSource: CurrencyNetworkDataSource,
    private val currencyDao: CurrencyDao,
    private val currencyExchangeLocalDataStore: CurrencyExchangeLocalDataStore
): CurrencyRepository {
    override fun getCurrencyList(): Observable<List<Currency>> {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(1800000)
        val currentTime = System.currentTimeMillis()
        if (currencyExchangeLocalDataStore.readLong(LocalDataStoreConstant.LAST_FETCH_CURRENCY_TIMESTAMP) + minutes > currentTime) {
            return networkDataSource.getAllCurrencyList()
                .map { jsonObject ->
                    return@map jsonObject.keySet().map { key ->
                        Currency(key, jsonObject[key].asString)
                    }
                }
                .map { remoteData ->
                    val currentCurrency = currencyDao.getAll()
                    val same = currentCurrency -remoteData.toSet()
                    currencyDao.delete(same.map{it.code})
                    currencyDao.insertAll(remoteData)
                    currencyExchangeLocalDataStore.writeLong(LocalDataStoreConstant.LAST_FETCH_CURRENCY_TIMESTAMP, currentTime)
                    return@map remoteData
                }
        } else {
            return currencyDao.getAllObservable()
        }
    }
}