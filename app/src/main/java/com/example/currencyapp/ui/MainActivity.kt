package com.example.currencyapp.ui

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.currencyapp.R
import com.example.currencyapp.databinding.ActivityMainBinding
import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.ui.common.Loading
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), MainContract.View {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
//    @Inject lateinit var mainFragmentFactory: MainFragmentFactory
    @Inject
    lateinit var presenter: MainContract.Presenter
    @Inject
    lateinit var currencyRepository: CurrencyRepository
    @Inject
    lateinit var currencyExchangeRateRepository: CurrencyExchangeRateRepository
    lateinit var loading: Loading
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        loading = Loading(this@MainActivity)

        setContentView(binding.root)
        presenter.initializeData()
        Observable.zip(currencyRepository.getCurrencyList(), currencyExchangeRateRepository.getExchangeRate())
        {
                currencyList, exchangeRate ->
//            Log.d("YULI", "TEST AJA:${currencyList[0].name}")
            //TODO: KENAPA BENTUK PAIR NYA GITU YA
            Pair(
                currencyList.map {("${it.code} - ${it.name}")},
                exchangeRate
            )
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()

//        GlobalScope.launch{
//
//            Log.d("repository.call()","repository.call():"+repository.getCurrencyList())
//        }


//        setSupportActionBar(binding.toolbar)
//        binding.name.text = viewModel.name
//        binding.button.setOnClickListener { viewModel.userClicked() }
//        supportFragmentManager.fragmentFactory = mainFragmentFactory
//        val navController = findNavController(R.id.nav_host_fragment_content_main)
//        appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)

//        binding.fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
    }

    override fun showLoading() {
        Log.d("YULI", "showLoading")
        loading.show()
    }

    override fun hideLoading() {
        Log.d("YULI", "hideLoading")
        loading.hide()
    }

    override fun showRetry() {
        Log.d("YULI", "showRetry")
        TODO("Not yet implemented")
    }

    override fun showError(error: String?) {
        Log.d("YULI", "showError:"+error)
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showAvailableCurrency(currencies: List<String>) {
        Log.d("YULI", "showAvailableCurrency")
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies)


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.spinner.setAdapter(dataAdapter)
    }

    override fun showCalculatedOtherCurrency(otherCurrencies: List<ExchangeRate>) {
        TODO("Not yet implemented")
    }
}