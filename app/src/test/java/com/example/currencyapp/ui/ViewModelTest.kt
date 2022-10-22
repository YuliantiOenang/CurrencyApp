package com.example.currencyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyapp.common.Common.getOrAwaitValue
import com.example.currencyapp.database.entity.Currency
import com.example.currencyapp.repository.CurrencyExchangeRateRepository
import com.example.currencyapp.repository.CurrencyRepository
import io.mockk.*
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ViewModelTest {
    private lateinit var viewModel: ViewModel
    private val currencyRepository: CurrencyRepository = mockk(relaxed = true)
    private val currencyExchangeRepository: CurrencyExchangeRateRepository = mockk(relaxed = true)
    private val disposable: CompositeDisposable = mockk(relaxed = true)
    private val subscriberScheduler = Schedulers.trampoline()
    private val observerScheduler = Schedulers.trampoline()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        viewModel = spyk(
            ViewModel(
                currencyRepository,
                currencyExchangeRepository,
                subscriberScheduler,
                observerScheduler,
                disposable
            ), recordPrivateCalls = true
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `Initialize Currency List should return list of currency list`() {
        every { currencyRepository.getCurrencyList() } returns Observable.just(
            listOf(
                Currency("USD", "United State Dollar"),
                Currency("IDR", "Indonesian Rupiah")
            )
        )

        every { currencyExchangeRepository.getExchangeRate() } returns Observable.just(
            listOf(
                com.example.currencyapp.database.entity.ExchangeRate(
                    "USD", "IDR", 1000.0
                ),
                com.example.currencyapp.database.entity.ExchangeRate(
                    "USD", "USD", 1.0
                ),
            )
        )

        val expectedResult = listOf(
            "USD - United State Dollar",
            "IDR - Indonesian Rupiah"
        )

        viewModel.initializeData()
        //Assert
        verify(exactly = 1) {
            currencyRepository.getCurrencyList()
            currencyExchangeRepository.getExchangeRate()
        }
        assertEquals(viewModel.currencyAvailableData.getOrAwaitValue(), expectedResult)
        assertEquals(viewModel.isRefreshing.getOrAwaitValue(), false)
        verify(exactly = 1) { disposable.add(any()) }
    }

    @Test
    fun `Error initialize Currency List should return error message`() {
        every { currencyRepository.getCurrencyList() } returns Observable.just(
            listOf(
                Currency("USD", "United State Dollar"),
                Currency("IDR", "Indonesian Rupiah")
            )
        )
        every { currencyExchangeRepository.getExchangeRate() } returns Observable.error(Throwable("error"))

        viewModel.initializeData()
        //Assert
        verify(exactly = 1) {
            currencyRepository.getCurrencyList()
            currencyExchangeRepository.getExchangeRate()
        }
        assertEquals(viewModel.error.getOrAwaitValue(), "error")
        assertEquals(viewModel.isRefreshing.getOrAwaitValue(), false)
        verify(exactly = 1) { disposable.add(any()) }
    }

    @Test
    fun `Calculate other currency should return all exchange rate`() {
        every { currencyExchangeRepository.getExchangeRate() } returns Observable.just(
            listOf(
                com.example.currencyapp.database.entity.ExchangeRate(
                    "USD", "IDR", 1000.0
                ),
                com.example.currencyapp.database.entity.ExchangeRate(
                    "USD", "USD", 1.0
                ),
            )
        )

        every { currencyExchangeRepository.getExchangeRate("USD", "IDR") } returns Observable.just(
            com.example.currencyapp.database.entity.ExchangeRate(
                "USD", "IDR", 1000.0
            )
        )

        val expectedResult = listOf(ExchangeRate("IDR", 20000.0), ExchangeRate("USD", 20.0))

        viewModel.calculateOtherCurrency("IDR", 20000.0)
        //Assert
        verify(exactly = 1) {
            currencyExchangeRepository.getExchangeRate()
            currencyExchangeRepository.getExchangeRate(any(), any())
        }
        assertEquals(viewModel.isRefreshing.getOrAwaitValue(), false)
        assertEquals(viewModel.exchangeRateData.getOrAwaitValue(), expectedResult)
        verify(exactly = 1) { disposable.add(any()) }
    }

    @Test
    fun `Error calculate other currency should return error message`() {
        every { currencyExchangeRepository.getExchangeRate() } returns Observable.just(
            listOf(
                com.example.currencyapp.database.entity.ExchangeRate(
                    "USD", "IDR", 1000.0
                ),
                com.example.currencyapp.database.entity.ExchangeRate(
                    "USD", "USD", 1.0
                ),
            )
        )

        every { currencyExchangeRepository.getExchangeRate("USD", "IDR") } returns Observable.error(
            Throwable("Error")
        )

        viewModel.calculateOtherCurrency("IDR", 20000.0)
        //Assert
        verify(exactly = 1) {
            currencyExchangeRepository.getExchangeRate()
            currencyExchangeRepository.getExchangeRate(any(), any())
        }
        assertEquals(viewModel.isRefreshing.getOrAwaitValue(), false)
        assertEquals(viewModel.error.getOrAwaitValue(), "Error")
        verify(inverse = true) { viewModel.exchangeRateData.postValue(any()) }
        verify(exactly = 1) { disposable.add(any()) }
    }
}