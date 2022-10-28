package com.example.garissaestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.garissaestore.model.domain.Product
import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val productsRepository: ProductRepository
): ViewModel() {


    fun refreshProducts() = viewModelScope.launch {
        val products: List<Product> = productsRepository.fetchAllProducts()
        store.update { applicationState ->
            return@update applicationState.copy(
                products = products
            )
        }
    }
}