package com.example.currencyapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.currencyapp.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings
import javax.inject.Inject

@WithFragmentBindings
@AndroidEntryPoint
class MainFragment : Fragment(), MainContract.View {

//    constructor() : super()

    @Inject
    lateinit var presenter: MainContract.Presenter
    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        presenter.initializeData()
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
//        val view = inflater.inflate(R.layout.fragment_first, null)
//        _binding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_first)
//        _binding = FragmentFirstBinding.bind(view)
//        binding.viewModel = ViewModel(Model(123000, "IDR"))

//        binding.etNominal.text = "123000"
        binding.currencyLira1.text = "AED"
        binding.currencyLira2.text = "AFN"
        binding.currencyLira3.text = "ALL"
        binding.currencyLira4.text = "AUD"
        binding.currencyLira5.text = "MXN"
        binding.currencyLira6.text = "EUR"
        //binding.viewModel
        return binding.root

    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding.buttonFirst.setOnClickListener {
//            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun showLoading() {
        TODO("Not yet implemented")
    }

    override fun hideLoading() {
        TODO("Not yet implemented")
    }

    override fun showRetry() {
        TODO("Not yet implemented")
    }

    override fun showError(error: String?) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun showAvailableCurrency(currencies: List<String>) {
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, currencies)


        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)


        binding.spinner.setAdapter(dataAdapter)
    }

    override fun showCalculatedOtherCurrency(otherCurrencies: List<ExchangeRate>) {
        TODO("Not yet implemented")
    }
}