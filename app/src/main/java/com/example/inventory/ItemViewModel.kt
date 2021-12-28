package com.example.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.inventory.data.Item
import com.example.inventory.data.ItemDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class ItemViewModel(
    private val itemDao: ItemDao,
    private val coroutineContext: CoroutineContext = Dispatchers.IO,
) : ViewModel() {
    val items: LiveData<List<Item>> = itemDao.query().asLiveData()

    fun getItem(itemId: Int): Flow<Item> {
        return itemDao.getItem(itemId)
    }

    fun sellItem(itemId: Int) {
        viewModelScope.launch(coroutineContext) {
            itemDao.sell(itemId)
        }
    }

    fun updateItem(item: Item) {
        viewModelScope.launch(coroutineContext) {
            itemDao.update(item)
        }
    }

    fun insertItem(item: Item) {
        viewModelScope.launch(coroutineContext) {
            itemDao.insert(item)
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch(coroutineContext) {
            itemDao.delete(itemId)
        }
    }

    fun clearItems() {
        viewModelScope.launch(coroutineContext) {
            itemDao.clear()
        }
    }
}