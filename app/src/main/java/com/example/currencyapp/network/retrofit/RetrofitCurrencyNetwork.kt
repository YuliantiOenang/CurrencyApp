package com.example.currencyapp.network.retrofit;

import android.util.Log
import com.example.currencyapp.BuildConfig
import com.example.currencyapp.network.CurrencyNetworkDataSource
import com.example.currencyapp.network.model.CurrencyExchangeRateModel
import io.reactivex.Observable
import com.google.gson.JsonObject
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import retrofit2.http.GET
import javax.inject.Inject
import javax.inject.Singleton

private interface RetrofitCurrencyNetworkApi {
    @GET("currencies.json")
    fun getCurrenciesApi(): Observable<JsonObject>
    @GET("latest.json")
    fun getCurrentExchange(): Observable<CurrencyExchangeRateModel>
}

@Singleton
class RetrofitCurrencyNetwork @Inject constructor(): CurrencyNetworkDataSource {
    private val loggingInterceptor = HttpLoggingInterceptor().apply{
        setLevel(
//        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
//        } else {
//            HttpLoggingInterceptor.Level.NONE
//        }
    )
    }

    private val networkApi = Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .client(
                OkHttpClient.Builder()
                .addNetworkInterceptor {
                    val original: Request = it.request()
                    val originalHttpUrl: HttpUrl = original.url

                    val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("app_id", "178e01e4ae7f4a5a89f2c9d71ac389f1")
                        .build()
                    //TODO: APAKAH PERLU TAMBAHIN QUERY PARAMETERS UNTUK BASE(FROM), SYMBOLS(TO)

                    val requestBuilder: Request.Builder = original.newBuilder()
                        .url(url)

                    val request: Request = requestBuilder.build()
                    return@addNetworkInterceptor it.proceed(request)
                }
                .addNetworkInterceptor(loggingInterceptor)
                .build()
            )
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create<RetrofitCurrencyNetworkApi>()
    override fun getAllCurrencyList(): Observable<JsonObject> {
        return networkApi.getCurrenciesApi()
    }

    override fun getConvertRate(): Observable<CurrencyExchangeRateModel> {
        return networkApi.getCurrentExchange()
    }
}
