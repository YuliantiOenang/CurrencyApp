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
    fun provideActivity(activity: Activity): MainActivity {
        return activity as MainActivity
    }
}