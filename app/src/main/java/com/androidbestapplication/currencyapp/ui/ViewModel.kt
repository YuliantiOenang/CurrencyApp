package com.androidbestapplication.currencyapp.ui

import androidx.lifecycle.MutableLiveData
import com.androidbestapplication.currencyapp.repository.CurrencyExchangeRateRepository
import com.androidbestapplication.currencyapp.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ViewModel @Inject constructor(
    var currencyRepository: CurrencyRepository,
    var currencyExchangeRateRepository: CurrencyExchangeRateRepository,
    @Named("subscriber") private val subscriberScheduler: Scheduler,
    @Named("observer") private val observerScheduler: Scheduler,
    private var disposable: CompositeDisposable
) : androidx.lifecycle.ViewModel() {
    var exchangeRateData: MutableLiveData<List<ExchangeRate>> = MutableLiveData<List<ExchangeRate>>()
    var currencyAvailableData : MutableLiveData<List<String>> = MutableLiveData<List<String>>()
    var isRefreshing: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var error: MutableLiveData<String> = MutableLiveData<String>()

    fun initializeData() {
        isRefreshing.postValue(true)
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

            .subscribeOn(subscriberScheduler)
            .observeOn(observerScheduler)
            .subscribe({}, {
                    e-> error.postValue(e.message)
            })
            .let {
                disposable.add(it)
                isRefreshing.postValue(false)
            }
    }

    fun calculateOtherCurrency(selectedCountry: String, amount: Double) {
        isRefreshing.postValue(true)
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
                return@zipWith otherCurrencies.map { it ->
                    val otherCurrencyAmount = usdAmount*it.rate
                    return@map ExchangeRate(
                        it.to,
                        otherCurrencyAmount
                    )
                }
            }
            .subscribe(
                {
                    isRefreshing.postValue(false)
                    exchangeRateData.postValue(it)
                }, {
                        e ->
                    isRefreshing.postValue(false)
                    error.postValue(e.message)
                }
            ).let {
                disposable.add(it)
            }
    }

    fun clearRequest() {
        disposable.dispose()
    }
}