package com.example.currencyapp.database.entity

import androidx.room.*
//TODO: Apakah harus dibuat relationnya foreign key? kalau gitu bukannya request datanya jadi mesti ada urutanny ya
//harus request all currency dulu, baru request exchange rate
@Entity(tableName = "exchange_rate",
    primaryKeys = ["from_currency", "to_currency"],
    foreignKeys = [
        ForeignKey(
            entity = Currency::class,
            parentColumns = ["currency_code"],
            childColumns = ["from_currency"]
        ),

        ForeignKey(
            entity = Currency::class,
            parentColumns = ["currency_code"],
            childColumns = ["to_currency"]
        ),
    ],
    indices = [
        Index(value = ["from_currency"]),
        Index(value = ["to_currency"])
    ]
)
data class ExchangeRate(
    @ColumnInfo(name = "from_currency", defaultValue = "") val from: String,
    @ColumnInfo(name = "to_currency", defaultValue = "") val to: String,
    @ColumnInfo(name = "rate") val rate: Double
    )
