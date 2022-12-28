package com.example.currencyapp.sharedPreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.currencyapp.sharedPreferences.LocalDataStoreConstant.SHARED_PREFERENCE_TAG
import javax.inject.Inject

class SharedPreferenceLocalDataStore @Inject constructor(context: Application): CurrencyExchangeLocalDataStore {
    private val sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_TAG, Context.MODE_PRIVATE)
    }

    override fun writeString(key: String, value: String) {
        with (sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    override fun readString(key: String): String? {
        return sharedPreferences.getString(key, null)
    }

    override fun writeLong(key: String, value: Long) {
        with (sharedPreferences.edit()) {
            putLong(key, value)
            apply()
        }
    }

    override fun readLong(key: String): Long {
        return sharedPreferences.getLong(key, 0)
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

}