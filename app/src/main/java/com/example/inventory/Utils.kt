package com.example.inventory

import androidx.fragment.app.Fragment
import com.example.inventory.data.InventoryDatabase

fun Fragment.getDatabase(): InventoryDatabase {
    return (requireActivity().application as InventoryApplication).inventoryDatabase
}