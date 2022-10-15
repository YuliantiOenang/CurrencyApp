package com.example.currencyapp.ui.di

import androidx.fragment.app.Fragment
import com.example.currencyapp.ui.CurrencyPresenter
import com.example.currencyapp.ui.MainContract
import com.example.currencyapp.ui.MainFragment
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface MainModule {
    @Binds
    fun provideView(mainFragment: MainFragment): MainContract.View

    @Binds
    fun providePresenter(presenter: CurrencyPresenter): MainContract.Presenter
}

@Module
@InstallIn(SingletonComponent::class)
object MainProviderModule {
    @Provides
    fun provideMainFragment(fragment: Fragment): MainFragment {
        return fragment as MainFragment
    }

    @Provides
    fun provideMainFragment(context: ApplicationContext): Fragment {
        return context
    }
}