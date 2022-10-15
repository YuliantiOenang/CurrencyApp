package com.example.currencyapp.ui.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.currencyapp.ui.CurrencyPresenter
import com.example.currencyapp.ui.MainActivity
import com.example.currencyapp.ui.MainContract
import com.example.currencyapp.ui.MainFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
@InstallIn(ActivityComponent::class)
interface MainModule {
    @Binds
    fun provideView(mainFragment: MainActivity): MainContract.View

    @Binds
    fun providePresenter(presenter: CurrencyPresenter): MainContract.Presenter
}

@Module
@InstallIn(ActivityComponent::class)
object MainProviderModule {
    @Provides
    @Named("subscriber")
    fun provideSubscriberScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named("observer")
    fun provideObserverScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    fun provideActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}