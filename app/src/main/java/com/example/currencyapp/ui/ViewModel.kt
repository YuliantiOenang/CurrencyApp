package com.example.currencyapp.ui

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

//@HiltViewModel
class ViewModel : androidx.lifecycle.ViewModel() {
//    @Inject
//    lateinit var currencyRepository: CurrencyRepository
//
//    @Inject
//    lateinit var currencyExchangeRateRepository: CurrencyExchangeRateRepository
//
//    var data = arrayListOf<Pair<String, List<ExchangeRate>>>()
//    var dataString= MutableLiveData<List<String>>(mutableListOf())
//
//    fun initializeData() {
//        Observable.zip(currencyRepository.getCurrencyList(), currencyExchangeRateRepository.getExchangeRate())
//        {
//                currencyList, exchangeRate ->
//            //TODO: KENAPA BENTUK PAIR NYA GITU YA
//           Pair(
//                currencyList.map {("${it.code} - ${it.name}")},
//                exchangeRate
//           )
//            dataString.value = listOf(exchangeRate.toString())
//        }
//
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
//    }

//    private var userMutableLiveData: MutableLiveData<Currency>? = null
//
//    fun getUser(): LiveData<User?>? {
//        if (userMutableLiveData == null) {
//            userMutableLiveData = MutableLiveData<Any>()
//        }
//        return userMutableLiveData
//    }
//
//    @Bindable
//    fun getNominal(): Int {
//        return this.nominal
//    }
//
//    fun setNominal(nominal: Int) {
//        this.nominal = nominal
//        notifyPropertyChanged()
//    }
//
//    @Bindable
//    fun getPassword(): String {
//        return this.password
//    }
//
//    fun setPassword(password: String) {
//        password = password
//        notifyPropertyChanged(BR.password)
//    }
//
//    fun onLoginClicked() {
//        setBusy(0) //View.VISIBLE
//        Handler().postDelayed(Runnable {
//            val user = ViewModel(getEmail(), getPassword())
//            if (!user.isEmailValid()) {
//                errorEmail.set("Enter a valid email address")
//            } else {
//                errorEmail.set(null)
//            }
//            if (!user.isPasswordLengthGreaterThan5()) errorPassword.set("Password Length should be greater than 5") else {
//                errorPassword.set(null)
//            }
//            userMutableLiveData!!.setValue(user)
//            setBusy(8) //8 == View.GONE
//        }, 5000)
//    }


}