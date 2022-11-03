package com.example.garissaestore.cart

import android.view.View
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelCartEmptyBinding
import com.example.garissaestore.epoxy.ViewBindingKotlinModel

data class CartEmptyEpoxyModel(
    private val onClick: (View) -> Unit
) : ViewBindingKotlinModel<EpoxyModelCartEmptyBinding>(R.layout.epoxy_model_cart_empty) {

    override fun EpoxyModelCartEmptyBinding.bind() {
        button.setOnClickListener(onClick)
    }
}