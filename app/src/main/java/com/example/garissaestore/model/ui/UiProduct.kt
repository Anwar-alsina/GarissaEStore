package com.example.garissaestore.model.ui

import com.example.garissaestore.model.domain.Product

data class UiProduct (
    val product: Product,
    val isFavourite: Boolean = false,
    val isExpanded: Boolean = false,
    val isInCart: Boolean = false
        )