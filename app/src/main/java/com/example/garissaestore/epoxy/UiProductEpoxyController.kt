package com.example.garissaestore.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.example.garissaestore.ProductListViewModel
import com.example.garissaestore.model.ui.UiProduct
import kotlinx.coroutines.launch

class UiProductEpoxyController(
    private val viewModel: ProductListViewModel
): TypedEpoxyController<List<UiProduct>>() {
    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()) {
            repeat(7){
                val epoxyId = it + 1
                UiProductEpoxyModel(
                    uiProduct = null,
                    onFavouriteIconClicked = ::onFavouriteIconClicked,
                    onUiProductClicked = ::onUiProductClicked
                ).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { uiProduct ->
            UiProductEpoxyModel(
                uiProduct = uiProduct,
                onFavouriteIconClicked = ::onFavouriteIconClicked,
                onUiProductClicked = ::onUiProductClicked

            ).id(uiProduct.product.id).addTo(this)
        }

    }

    private fun onFavouriteIconClicked(selectedProductId: Int){
        viewModel.viewModelScope. launch {
            viewModel.store.update { currentState ->
                val currentFavouriteIds = currentState.favouriteProductIds
                val newFavouriteIds = if (currentFavouriteIds.contains(selectedProductId)) {
                    currentFavouriteIds.filter { it != selectedProductId }.toSet()
                } else{
                    currentFavouriteIds + setOf(selectedProductId)
                }
                return@update currentState.copy(favouriteProductIds = newFavouriteIds)
            }
        }

    }

    private fun onUiProductClicked(productId: Int){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentExpandedId = currentState.expandedProductIds
                val newExpandedIds = if (currentExpandedId.contains(productId)){
                    currentExpandedId.filter { it != productId }.toSet()
                }else {
                    currentExpandedId + setOf(productId)
                }
                return@update currentState.copy(expandedProductIds = newExpandedIds)
            }
        }
    }

}