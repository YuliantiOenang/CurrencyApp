package com.example.currencyapp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(tableName = "currency",
    primaryKeys = ["currency_code"],
    indices = [
        Index(value = ["currency_code"])
    ]
)
data class Currency (
    @ColumnInfo(name = "currency_code")
    val code:String,
    @ColumnInfo(name = "display_name")
    val name:String
)