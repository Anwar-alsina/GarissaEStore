package com.example.garissaestore.epoxy

import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.TypedEpoxyController
import com.example.garissaestore.ProductListViewModel
import com.example.garissaestore.UiProductFilterEpoxyModel
import com.example.garissaestore.model.domain.Filter
import com.example.garissaestore.model.ui.ProductListFragmentUiState
import com.example.garissaestore.model.ui.UiProduct
import kotlinx.coroutines.launch
import java.util.UUID

class UiProductEpoxyController(
    private val viewModel: ProductListViewModel
): TypedEpoxyController<ProductListFragmentUiState>() {
    override fun buildModels(data: ProductListFragmentUiState?) {

        when (data) {
            is ProductListFragmentUiState.Success -> {
                val uiFilterModels = data.filters.map { uiFilter ->
                    UiProductFilterEpoxyModel(
                        uiFilter = uiFilter,
                        onFilterSelected = ::onFilterSelected
                    ).id(uiFilter.filter.value)
                }

                CarouselModel_().models(uiFilterModels).id("filters").addTo(this)

                data.product.forEach { uiProduct ->
                    UiProductEpoxyModel(
                        uiProduct = uiProduct,
                        onFavouriteIconClicked = ::onFavouriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked

                    ).id(uiProduct.product.id).addTo(this)
                }
            }
            is ProductListFragmentUiState.Loading -> {
                repeat(7) {
                    val epoxyId = UUID.randomUUID().toString()
                    UiProductEpoxyModel(
                        uiProduct = null,
                        onFavouriteIconClicked = ::onFavouriteIconClicked,
                        onUiProductClicked = ::onUiProductClicked
                    ).id(epoxyId).addTo(this)
                }
            }
            else -> {
                throw RuntimeException("Unhandled branch! $data")
            }
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
    private fun onFilterSelected(filter: Filter){
        viewModel.viewModelScope.launch {
            viewModel.store.update { currentState ->
                val currentSelectedFilter = currentState.productFilterInfo.selectedFilter
                return@update currentState.copy(
                    productFilterInfo = currentState.productFilterInfo.copy(
                        selectedFilter = if (currentSelectedFilter != filter ){
                            filter
                        }else{
                            null
                        }
                    )
                )
            }
        }
    }

}