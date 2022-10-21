package com.example.currencyapp.ui

import android.util.Log
import android.widget.Toast
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
    var listCurrency = arrayListOf<ExchangeRate>()
    override fun initializeData() {
        view.showLoading()
        Observable.zip(currencyRepository.getCurrencyList(), currencyExchangeRateRepository.getExchangeRate())
        {
                currencyList, exchangeRate ->
            //TODO: KENAPA BENTUK PAIR NYA GITU YA
            listCurrency = exchangeRate.map { ExchangeRate(it.to, it.rate) } as ArrayList<ExchangeRate>
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
                    //view.showAvailableCurrency(it.first)
                }
            }, {
                e ->
                view.hideLoading()
                view.showError(e.message)
            }).let {
                disposables.add(it)
            }
    }

    override fun calculateOtherCurrency(selectedCountry: String, amount: Double) {
        view.showLoading()
//        Log.d("YULI", "calculateOtherCurrency:selectedCountry:"+selectedCountry.subSequence(0,3) as String)
//        Log.d("YULI", "calculateOtherCurrency:amount:"+amount)
        currencyExchangeRateRepository.getExchangeRate("USD",
            selectedCountry.subSequence(0,3) as String
        )
            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
            .map {
                exchangeRate -> amount/exchangeRate.rate
                //Log.d("YULI", "exchangeRate:"+amount/exchangeRate.rate)
            }
            .zipWith(currencyExchangeRateRepository.getExchangeRate()) { usdAmount, otherCurrencies ->
                // Then after getting all currency exchange rate, we calculate the usd amount to each currency exchange rate
                // The result is represented in OtherCurrencyItem
                // There's an improvement to show not only the result currency code but also the display name

                return@zipWith otherCurrencies.map {
                    val otherCurrencyAmount = usdAmount*it.rate
//                    Log.d("YULI", "otherCurrencyAmount:"+otherCurrencyAmount)
//                    Log.d("YULI", "usdAmount:"+usdAmount)
//                    Log.d("YULI", "it.rate:"+it.rate)
//                    Log.d("YULI", "it.to:"+it.to)
                    return@map ExchangeRate(
                        it.to,
                        otherCurrencyAmount
                    )
                }
            }
            .subscribe(
                {
                    view.hideLoading()
                    view.showCalculatedOtherCurrency(it)
                }, {
                    e ->
                    view.hideLoading()
                    view.showError(e.message)
                }
            )
    }

    override fun clearRequest() {
        TODO("Not yet implemented")
    }

}