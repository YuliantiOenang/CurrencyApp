package com.example.currencyapp.ui

import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ViewModel @Inject constructor(
    var currencyRepository: CurrencyRepository,
    var currencyExchangeRateRepository: CurrencyExchangeRateRepository,
    @Named("subscriber") private val subscriberScheduler: Scheduler,
    @Named("observer") private val observerScheduler: Scheduler
) : androidx.lifecycle.ViewModel() {
    val exchangeRateData: MutableLiveData<Pair<String, List<ExchangeRate>>> by lazy {
        MutableLiveData<Pair<String, List<ExchangeRate>>>()
    }
    var currencyAvailableData : MutableLiveData<List<String>> = MutableLiveData<List<String>>()

//    var data = arrayListOf<Pair<String, List<ExchangeRate>>>()
//    var dataString= MutableLiveData<List<String>>(mutableListOf())
//
    fun initializeData() {
        Observable.zip(currencyRepository.getCurrencyList(), currencyExchangeRateRepository.getExchangeRate())
        {
                currencyList, exchangeRate ->
           Pair(
                currencyList.map {("${it.code} - ${it.name}")},
                exchangeRate
           ).let {
               currencyAvailableData.postValue(it.first!!)
           }
        }

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun calculateOtherCurrency(selectedCountry: String, amount: Double) {
//        view.showLoading()
        currencyExchangeRateRepository.getExchangeRate("USD",
            selectedCountry.subSequence(0,3) as String
        )
            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
            .map {
                    exchangeRate -> amount/exchangeRate.rate
            }
            .zipWith(currencyExchangeRateRepository.getExchangeRate()) { usdAmount, otherCurrencies ->
                // Then after getting all currency exchange rate, we calculate the usd amount to each currency exchange rate
                // The result is represented in OtherCurrencyItem
                // There's an improvement to show not only the result currency code but also the display name

                return@zipWith otherCurrencies.map {
                    val otherCurrencyAmount = usdAmount*it.rate
                    return@map ExchangeRate(
                        it.to,
                        otherCurrencyAmount
                    )
                }
            }
            .subscribe(
                {
//                    view.hideLoading()
//                    view.showCalculatedOtherCurrency(it)
                }, {
                        e ->
//                    view.hideLoading()
//                    view.showError(e.message)
                }
            )
    }

    fun clearRequest() {
        TODO("Not yet implemented")
    }
}