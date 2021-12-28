package com.example.inventory

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.inventory.data.Item
import com.example.inventory.data.formattedPrice
import com.example.inventory.databinding.ItemListItemBinding

class ItemListAdapter(
    private val viewAction: (Item) -> Unit,
) : ListAdapter<Item, ItemListAdapter.ItemViewHolder>(ItemComparator) {

    inner class ItemViewHolder(private val binding: ItemListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {
            binding.apply {
                itemName.text = item.itemName
                itemPrice.text = item.formattedPrice
                itemQuantity.text = item.itemQuantity.toString()
                root.setOnClickListener {
                    viewAction(item)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val layoutItemBinding = ItemListItemBinding.inflate(inflater, parent, false)
        return ItemViewHolder(layoutItemBinding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}