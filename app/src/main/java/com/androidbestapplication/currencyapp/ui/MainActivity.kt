package com.androidbestapplication.currencyapp.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.androidbestapplication.currencyapp.R
import com.androidbestapplication.currencyapp.databinding.ActivityMainBinding
import com.androidbestapplication.currencyapp.ui.common.Loading
import com.androidbestapplication.currencyapp.ui.common.OtherCurrencyAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private lateinit var binding: ActivityMainBinding
    private lateinit var loading: Loading
    private lateinit var countryListAdapter: ArrayAdapter<String>
    private val adapter= OtherCurrencyAdapter()
    private val viewModel by viewModels<ViewModel>()
    private var searchJob: Job? = null

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
                }
            }
        }

        val countries = Observer<List<String>> { countries ->
            countryListAdapter= ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries)
            countryListAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

            binding.spinner.adapter = countryListAdapter
        }
        viewModel.currencyAvailableData.observe(this, countries)

        val exchangeRateObserver = Observer<List<ExchangeRate>> { exchangeRates ->
            adapter.setData(exchangeRates)
        }
        viewModel.exchangeRateData.observe(this, exchangeRateObserver)

        val refresh = Observer<Boolean> { isRefreshing ->
            if (isRefreshing) {
                loading.show()
            } else {
                loading.hide()
            }
        }
        viewModel.isRefreshing.observe(this, refresh)

        val error = Observer<String> { error ->
            Toast.makeText(this, error, Toast.LENGTH_LONG).show()
        }
        viewModel.error.observe(this, error)
    }

    override fun onDestroy() {
        viewModel.clearRequest()
        super.onDestroy()
    }
}