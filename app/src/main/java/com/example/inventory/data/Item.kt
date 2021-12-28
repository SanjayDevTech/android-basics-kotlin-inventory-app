package com.example.inventory.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.NumberFormat
import java.util.Locale
import java.util.Currency

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val id: Int = 0,
    @NonNull @ColumnInfo(name = "name")
    val itemName: String,
    @NonNull @ColumnInfo(name = "price")
    val itemPrice: Double,
    @NonNull @ColumnInfo(name = "quantity")
    val itemQuantity: Long,
)

val Item.formattedPrice: String
    get() {
        return NumberFormat.getCurrencyInstance(Locale.ENGLISH)
            .apply {
                currency = Currency.getInstance("USD")
            }
            .format(itemPrice)
    }
