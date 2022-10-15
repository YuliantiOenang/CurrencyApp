package com.example.currencyapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.node.getOrAddAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.currencyapp.R
import com.example.currencyapp.database.entity.Currency
import com.example.currencyapp.databinding.ActivityMainBinding
import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import com.example.currencyapp.ui.common.Loading
import com.example.currencyapp.ui.common.OtherCurrencyAdapter
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
    val adapter= OtherCurrencyAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        loading = Loading(this@MainActivity)

        setContentView(binding.root)
        presenter.initializeData()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this@MainActivity, 2)

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


        binding.button.setOnClickListener {
            if (binding.spinner.selectedItem != null && binding.etNominal.text.isNotEmpty()) {
                (binding.etNominal.text.toString()).toDoubleOrNull()?.let { it ->
                    presenter.calculateOtherCurrency(
                        binding.spinner.selectedItem.toString(),
                        it
                    )
                }
            }
        }
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
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, currencies)


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.spinner.setAdapter(dataAdapter)
    }

    override fun showCalculatedOtherCurrency(otherCurrencies: List<ExchangeRate>) {
        adapter.setData(otherCurrencies)
    }
}