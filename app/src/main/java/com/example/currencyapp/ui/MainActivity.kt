package com.example.currencyapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.node.getOrAddAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
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
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main), MainContract.View {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
//    @Inject
//    lateinit var presenter: MainContract.Presenter
    lateinit var loading: Loading
    val adapter= OtherCurrencyAdapter()
    private val viewModel by viewModels<ViewModel>()
    private var searchJob: Job? = null
    private lateinit var dataAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        loading = Loading(this@MainActivity)
        setContentView(binding.root)
        viewModel.initializeData()
        binding.rv.adapter = adapter
        binding.rv.layoutManager = GridLayoutManager(this@MainActivity, 2)

        binding.button.setOnClickListener {
            if (binding.spinner.selectedItem != null && binding.etNominal.text.isNotEmpty()) {
                (binding.etNominal.text.toString()).toDoubleOrNull()?.let { it ->
                    searchJob?.cancel()
                    searchJob = lifecycleScope.launch {
                        viewModel.calculateOtherCurrency(binding.spinner.selectedItem.toString(), it)
                    }
//                    presenter.calculateOtherCurrency(
//                        binding.spinner.selectedItem.toString(),
//                        it
//                    )
                }
            }
        }

        // Create the observer which updates the UI.
        val nameObserver = Observer<List<String>> { newName ->
            // Update the UI, in this case, a TextView.
            //binding.txtCoba.text = newName.toString()
            dataAdapter= ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, newName)
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spinner.setAdapter(dataAdapter)
        }

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.currencyAvailableData.observe(this, nameObserver)
    }

    override fun showLoading() {
        loading.show()
    }

    override fun hideLoading() {
        loading.hide()
    }

    override fun showRetry() {
        TODO("Not yet implemented")
    }

    override fun showError(error: String?) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }

    override fun showCalculatedOtherCurrency(otherCurrencies: List<ExchangeRate>) {
        adapter.setData(otherCurrencies)
    }
}