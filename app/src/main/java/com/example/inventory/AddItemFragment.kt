/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.inventory

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.inventory.data.Item
import com.example.inventory.databinding.FragmentAddItemBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Fragment to add or update an item in the Inventory database.
 */
class AddItemFragment : Fragment() {

    private val navigationArgs: ItemDetailFragmentArgs by navArgs()

    // Binding object instance corresponding to the fragment_add_item.xml layout
    // This property is non-null between the onCreateView() and onDestroyView() lifecycle callbacks,
    // when the view hierarchy is attached to the fragment
    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ItemViewModel by activityViewModels {
        ItemViewModelFactory(getDatabase().itemDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (navigationArgs.itemId != -1) {
            lifecycleScope.launch {
                val item = viewModel.getItem(navigationArgs.itemId).first()
                binding.apply {
                    itemName.setText(item.itemName)
                    itemPrice.setText(item.itemPrice.toString())
                    itemCount.setText(item.itemQuantity.toString())
                }
            }
        }
        binding.saveAction.setOnClickListener {
            val itemName = binding.itemName.text.toString()
            val itemPrice = binding.itemPrice.text.toString().toDoubleOrNull() ?: 0.0
            val itemQuantity = binding.itemCount.text.toString().toLongOrNull() ?: 0
            val isValidated = itemName.isNotBlank() && itemPrice > 0 && itemQuantity > 0
            if (isValidated) {
                viewModel.let {
                    val itemId = if (navigationArgs.itemId == -1) 0 else navigationArgs.itemId
                    val item = Item(
                        id = itemId,
                        itemName = itemName,
                        itemPrice = itemPrice,
                        itemQuantity = itemQuantity,
                    )
                    if (itemId == 0) it.insertItem(item) else it.updateItem(item)
                }
                val action = AddItemFragmentDirections.actionAddItemFragmentToItemListFragment()
                findNavController().navigate(action)
            } else {
                Snackbar.make(binding.root, "Entered details is invalid", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Called before fragment is destroyed.
     */
    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }
}
