package com.example.currencyapp.ui.common

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.currencyapp.R
import com.example.currencyapp.ui.ExchangeRate

class OtherCurrencyAdapter : RecyclerView.Adapter<OtherCurrencyAdapter.OtherCurrencyViewHolder>() {

    var data: MutableList<ExchangeRate> = mutableListOf()
    class OtherCurrencyViewHolder(view: View) : ViewHolder(view) {
        val textViewCurrency: TextView
        val textViewAmountConverted: TextView

        init {
            // Define click listener for the ViewHolder's View.
            textViewCurrency = view.findViewById(R.id.item_view_currency)
            textViewAmountConverted = view.findViewById(R.id.item_view_converted_amount)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OtherCurrencyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder, parent, false)

        return OtherCurrencyViewHolder(view)
    }

    override fun onBindViewHolder(holder: OtherCurrencyViewHolder, position: Int) {
        holder.textViewAmountConverted.text = data[position].rate.toString()
        holder.textViewCurrency.text = data[position].name
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @JvmName("setNewData")
    fun setData(newData: List<ExchangeRate>) {
        data.clear()
        data.addAll(newData)
        Log.d("YULI","update data,data:"+data.get(0))
        notifyDataSetChanged()
    }
}