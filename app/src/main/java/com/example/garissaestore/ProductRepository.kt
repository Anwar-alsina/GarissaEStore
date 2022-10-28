package com.example.garissaestore

import com.example.garissaestore.model.domain.Product
import com.example.garissaestore.model.mapper.ProductMapper
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val productsService: ProductsService,
    private val productMapper: ProductMapper
) {

    suspend fun fetchAllProducts(): List<Product>{
        //todo better error handling
        return productsService.getAllProducts().body()?.let { networkProducts -> networkProducts.map { productMapper.buildFrom(it) }
        } ?: emptyList()
    }

}
