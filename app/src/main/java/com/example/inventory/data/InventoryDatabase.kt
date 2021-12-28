package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile
        private var INSTANCE: InventoryDatabase? = null
        fun getInstance(context: Context): InventoryDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance: InventoryDatabase = Room.databaseBuilder(
                    context,
                    InventoryDatabase::class.java,
                    "inventory.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}