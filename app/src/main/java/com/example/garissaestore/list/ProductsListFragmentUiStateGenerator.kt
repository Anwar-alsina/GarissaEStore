package com.example.garissaestore.list

import com.example.garissaestore.model.ui.ProductListFragmentUiState
import com.example.garissaestore.model.ui.UiFilter
import com.example.garissaestore.model.ui.UiProduct
import com.example.garissaestore.redux.ApplicationState
import javax.inject.Inject

class ProductsListFragmentUiStateGenerator @Inject constructor() {

    fun generate(
        uiProducts: List<UiProduct>,
        productFilterInfo: ApplicationState.ProductFilterInfo
    ): ProductListFragmentUiState{
        if (uiProducts.isEmpty()){
            return ProductListFragmentUiState.Loading
        }

        val uiFilters = productFilterInfo.filters.map { filter ->
            UiFilter(
                filter =  filter,
                isSelected = productFilterInfo.selectedFilter?.equals(filter) == true
            )
        }.toSet()

        val filteredProducts = if (productFilterInfo.selectedFilter == null){
            uiProducts
        }else{
            uiProducts.filter { it.product.category == productFilterInfo.selectedFilter.value }
        }

        return ProductListFragmentUiState.Success(uiFilters,filteredProducts)
    }
}