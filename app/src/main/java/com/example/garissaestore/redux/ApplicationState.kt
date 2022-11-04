package com.example.garissaestore.redux

import com.example.garissaestore.model.domain.Filter
import com.example.garissaestore.model.domain.Product

data class ApplicationState(
    val products: List<Product> = emptyList(),
    val productFilterInfo: ProductFilterInfo = ProductFilterInfo(),
    val favouriteProductIds: Set<Int> = emptySet(),
    val expandedProductIds: Set<Int> = emptySet(),
    val inCartProductIds: Set<Int> = emptySet(),
    val cartQuantitiesMap: Map<Int,Int> = emptyMap() // productId -> quantity
){
    data class ProductFilterInfo(
        val filters: Set<Filter> = emptySet(),
        val selectedFilter: Filter? = null
    )
}
