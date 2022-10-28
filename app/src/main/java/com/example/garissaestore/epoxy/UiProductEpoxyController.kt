package com.example.garissaestore.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.TypedEpoxyController
import com.example.garissaestore.MainActivityViewModel
import com.example.garissaestore.model.ui.UiProduct
import kotlinx.coroutines.launch

class UiProductEpoxyController(
    private val viewModel: MainActivityViewModel
): TypedEpoxyController<List<UiProduct>>() {
    override fun buildModels(data: List<UiProduct>?) {
        if (data.isNullOrEmpty()) {
            repeat(7){
                val epoxyId = it + 1
                UiProductEpoxyModel(
                    uiProduct = null,
                    onFavouriteIconClicked = ::onFavouriteIconClicked
                ).id(epoxyId).addTo(this)
            }
            return
        }

        data.forEach { uiProduct ->
            UiProductEpoxyModel(
                uiProduct = uiProduct,
                onFavouriteIconClicked = ::onFavouriteIconClicked
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

}