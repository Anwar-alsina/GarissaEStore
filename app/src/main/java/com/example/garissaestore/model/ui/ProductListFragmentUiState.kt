package com.example.garissaestore.model.ui

data class ProductListFragmentUiState(
    val filters: Set<UiFilter>,
    val product: List<UiProduct>
)
