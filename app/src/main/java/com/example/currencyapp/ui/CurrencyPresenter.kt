package com.example.currencyapp.ui

import android.util.Log
import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

class CurrencyPresenter @Inject constructor(
    var currencyRepository: CurrencyRepository,
    var currencyExchangeRateRepository: CurrencyExchangeRateRepository,
    val view: MainContract.View,
    @Named("subscriber") private val subscriberScheduler: Scheduler,
    @Named("observer") private val observerScheduler: Scheduler,
    private val disposables: CompositeDisposable
) : MainContract.Presenter {
    override fun initializeData() {
        view.showLoading()
        Observable.zip(currencyRepository.getCurrencyList(), currencyExchangeRateRepository.getExchangeRate())
        {
                currencyList, exchangeRate ->
            Log.d("YULI", "TEST AJA:${currencyList[0].name}")
            //TODO: KENAPA BENTUK PAIR NYA GITU YA
            Pair(
                currencyList.map {("${it.code} - ${it.name}")},
                exchangeRate
            )
        }
            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
            .subscribe({
                view.hideLoading()
                if (it.first.isEmpty() || it.second.isEmpty()) {
                    //view.showRetryDialog()
                } else {
                    view.showAvailableCurrency(it.first)
                }
            }, {
                e ->
                view.hideLoading()
                view.showError(e.message)
            }).let {
                disposables.add(it)
            }
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