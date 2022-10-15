package com.example.currencyapp.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import javax.inject.Inject

class MainFragmentFactory @Inject constructor(
    private val presenter: MainContract.Presenter
    // your dependencies
) : FragmentFactory() {
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment = when(className) {
//        MainFragment::class.java.name -> MainFragment(presenter)
//        // here you can add every other fragment
//        else -> super.instantiate(classLoader, className)
//    }
}