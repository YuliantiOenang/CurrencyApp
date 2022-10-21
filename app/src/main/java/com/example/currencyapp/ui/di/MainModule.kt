package com.example.currencyapp.ui.di

import android.app.Activity
import com.example.currencyapp.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object MainModelModule {
    @Provides
    @Named("subscriber")
    fun provideSubscriberScheduler(): Scheduler = Schedulers.io()

    @Provides
    @Named("observer")
    fun provideObserverScheduler(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
}

@Module
@InstallIn(ActivityComponent::class)
object MainProviderModule {
    @Provides
    fun provideActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }
}