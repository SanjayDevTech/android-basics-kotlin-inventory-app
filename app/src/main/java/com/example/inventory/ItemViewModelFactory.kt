package com.example.inventory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class ItemViewModelFactory(
    private val itemDao: ItemDao,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            return ItemViewModel(itemDao = itemDao, coroutineContext = coroutineContext) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}