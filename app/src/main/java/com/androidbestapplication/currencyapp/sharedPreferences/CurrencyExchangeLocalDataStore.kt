package com.androidbestapplication.currencyapp.sharedPreferences

interface CurrencyExchangeLocalDataStore {
    fun writeString(key:String, value:String)
    fun readString(key:String): String?
    fun writeLong(key:String, value:Long)
    fun readLong(key:String): Long
    fun clear()
}