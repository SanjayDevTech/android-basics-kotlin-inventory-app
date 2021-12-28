package com.example.inventory

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.inventory.data.InventoryDatabase

fun Fragment.getDatabase(): InventoryDatabase {
    return (requireActivity().application as InventoryApplication).inventoryDatabase
}

fun Activity.getDatabase(): InventoryDatabase {
    return (application as InventoryApplication).inventoryDatabase
}