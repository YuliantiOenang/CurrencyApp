package com.androidbestapplication.currencyapp.ui.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Window
import com.androidbestapplication.currencyapp.databinding.DialogLoadingBinding

class Loading(context:Context) : Dialog(context) {
    private val binding: DialogLoadingBinding by lazy {
        DialogLoadingBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(binding.root)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
    }
}