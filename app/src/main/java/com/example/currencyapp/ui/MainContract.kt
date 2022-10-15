package com.example.currencyapp.ui

interface MainContract {
    interface Presenter {
        fun initializeData()
        fun calculateOtherCurrency(selectedCountry: String, amount: Double)
        fun clearRequest()
    }

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showRetry()
        fun showError(error: String?)
        fun showAvailableCurrency(currencies: List<String>)
        fun showCalculatedOtherCurrency(otherCurrencies: List<ExchangeRate>)
    }
}