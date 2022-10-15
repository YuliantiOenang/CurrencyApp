package com.example.currencyapp.network.model

data class CurrencyExchangeRateModel (
    val base: String,
    val rates: Map<String, Double>
)

