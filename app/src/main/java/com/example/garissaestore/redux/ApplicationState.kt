package com.example.garissaestore.redux

import com.example.garissaestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList()
)
