package com.example.currencyapp.repository

import android.util.Log
import com.example.currencyapp.database.dao.ExchangeRateDao
import com.example.currencyapp.database.entity.ExchangeRate
import com.example.currencyapp.network.CurrencyNetworkDataSource
import com.example.currencyapp.sharedPreferences.CurrencyExchangeLocalDataStore
import com.example.currencyapp.sharedPreferences.LocalDataStoreConstant.LAST_FETCH_EXCHANGE_RATE_TIMESTAMP
import io.reactivex.Observable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class OfflineCurrencyExchangeRateRepository @Inject constructor(
    private val networkDataSource: CurrencyNetworkDataSource,
    val dao: ExchangeRateDao,
    private val currencyExchangeLocalDataStore: CurrencyExchangeLocalDataStore
) :
    CurrencyExchangeRateRepository {
    override fun getExchangeRate(): Observable<List<ExchangeRate>> {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(1800000)
        val currentTime = System.currentTimeMillis()
        val timeSaved = currencyExchangeLocalDataStore.readLong(LAST_FETCH_EXCHANGE_RATE_TIMESTAMP)
        if ((timeSaved < 0.5) || (currencyExchangeLocalDataStore.readLong(LAST_FETCH_EXCHANGE_RATE_TIMESTAMP) + minutes > currentTime)) {
            return networkDataSource.getConvertRate().map {
                return@map it.rates.map { entry ->
                    ExchangeRate(it.base, entry.key, entry.value)
                }
            }
                .map{
                    exchangeCurrency ->
                    val same = dao.getAll() - exchangeCurrency.toSet()
                    dao.deleteById(same)
                    dao.insertAll(exchangeCurrency)
                    currencyExchangeLocalDataStore.writeLong(LAST_FETCH_EXCHANGE_RATE_TIMESTAMP, currentTime)
                    return@map exchangeCurrency
                }
        } else {
            Log.d("YULI", "MASUK SINI: getExchangeRate DAO")
            return dao.getAllObservable()
        }
    }

    override fun getExchangeRate(from: String, to: String): Observable<ExchangeRate> {
        return dao.getExchangeRate(from, to)
    }

}