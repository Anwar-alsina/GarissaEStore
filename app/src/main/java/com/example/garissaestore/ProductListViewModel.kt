package com.example.garissaestore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garissaestore.model.domain.Filter
import com.example.garissaestore.model.domain.Product
import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProductListViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productsRepository: ProductRepository
): ViewModel() {


    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productsRepository.fetchAllProducts()
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products,
                productFilterInfo = ApplicationState.ProductFilterInfo(
                    filters = products.map { Filter(value = it.category, displayText = it.category)
                    }.toSet(),
                    selectedFilter = null
                )
            )
        }
    }
}