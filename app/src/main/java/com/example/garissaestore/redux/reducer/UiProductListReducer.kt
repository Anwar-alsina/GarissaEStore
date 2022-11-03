package com.example.garissaestore.redux.reducer


import com.example.garissaestore.model.ui.UiProduct
import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UiProductListReducer @Inject constructor() {
    fun reduce(store: Store<ApplicationState>): kotlinx.coroutines.flow.Flow<List<UiProduct>>{
        return combine(
            store.stateFlow.map { it.products},
            store.stateFlow.map { it.favouriteProductIds },
            store.stateFlow.map { it.expandedProductIds },
            store.stateFlow.map { it.inCartProductIds }
        ) { listOfProducts, setOfFavouriteIds, setOfExpandedProductIds, inCartProductIds ->

            if (listOfProducts.isEmpty()) {
                return@combine emptyList<UiProduct>()
            }

            return@combine listOfProducts.map { product ->
                UiProduct(
                    product = product,
                    isFavourite = setOfFavouriteIds.contains(product.id),
                    isExpanded = setOfExpandedProductIds.contains(product.id),
                    isInCart = inCartProductIds.contains(product.id)
                )
            }
        }
    }
}




