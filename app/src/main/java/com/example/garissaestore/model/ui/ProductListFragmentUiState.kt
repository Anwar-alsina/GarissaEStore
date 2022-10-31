package com.example.garissaestore.model.ui

sealed interface ProductListFragmentUiState{

    data class Success(
        val filters: Set<UiFilter>,
        val product: List<UiProduct>
    ): ProductListFragmentUiState

    object Loading: ProductListFragmentUiState
}
