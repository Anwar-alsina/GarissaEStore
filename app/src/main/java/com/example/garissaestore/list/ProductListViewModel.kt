package com.example.garissaestore.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garissaestore.FilterGenerator
import com.example.garissaestore.model.domain.Filter
import com.example.garissaestore.model.domain.Product
import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import com.example.garissaestore.redux.reducer.UiProductListReducer
import com.example.garissaestore.redux.updater.UiProductFavouriteUpdater
import com.example.garissaestore.redux.updater.UiProductInCartUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavouriteUpdater: UiProductFavouriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater,
    private val productsRepository: ProductRepository,
    private val filterGenerator: FilterGenerator,
): ViewModel() {


    fun refreshProducts() = viewModelScope.launch {
        if (store.read { it.products }.isNotEmpty()) return@launch
        val products: List<Product> = productsRepository.fetchAllProducts()
        val filters: Set<Filter> = filterGenerator.generateFrom(products)
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products,
                productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = filters,
                    selectedFilter = applicationState.productFilterInfo.selectedFilter
                )
            )
        }
    }
}