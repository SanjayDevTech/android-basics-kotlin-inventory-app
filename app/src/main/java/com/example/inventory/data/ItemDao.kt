package com.example.inventory.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun query(): Flow<List<Item>>

    @Insert
    suspend fun insert(item: Item)

    @Update
    suspend fun update(item: Item)

    @Query("UPDATE item SET quantity = quantity - 1 WHERE id = :itemId")
    suspend fun sell(itemId: Int)

    @Query("SELECT * FROM item WHERE id = :itemId")
    fun getItem(itemId: Int): Flow<Item>

    @Query("DELETE FROM item WHERE id = :itemId")
    suspend fun delete(itemId: Int)

    @Query("DELETE FROM item")
    suspend fun clear()
}