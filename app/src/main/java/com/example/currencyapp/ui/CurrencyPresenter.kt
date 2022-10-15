package com.example.currencyapp.ui

import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyPresenter @Inject constructor(
    val currencyRepository: CurrencyRepository,
    val currencyExchangeRateRepository: CurrencyExchangeRateRepository,
    val view: MainContract.View
) : MainContract.Presenter {
    override fun initializeData() {
        view.showLoading()
        Observable.zip(currencyRepository.getCurrencyList(), currencyExchangeRateRepository.getExchangeRate())
        {
                currencyList, exchangeRate ->
            //TODO: KENAPA BENTUK PAIR NYA GITU YA
            Pair(
                currencyList.map {("${it.code} - ${it.name}")},
                exchangeRate
            )
        }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                map ->
                view.hideLoading()
                view.showAvailableCurrency(map.first)
            }, {
                e ->
                view.hideLoading()
                view.showError(e.message)
            }, {
                view.hideLoading()
            })
    }

    override fun calculateOtherCurrency(selectedCountry: String, amount: Long): Double {
        view.showLoading()
        currencyExchangeRateRepository.getExchangeRate("USD", selectedCountry)
            .map {
                exchangeRate ->
                return@map exchangeRate.rate*amount
            }
            .subscribe(
                {
                    view.hideLoading()
                }, {
                    e ->
                    view.hideLoading()
                    view.showError(e.message)
                }, {
                    view.hideLoading()
                }
            )
        return 0.0
    }

    override fun clearRequest() {
        TODO("Not yet implemented")
    }

}