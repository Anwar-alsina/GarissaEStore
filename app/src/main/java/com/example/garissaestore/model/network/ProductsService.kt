package com.example.garissaestore.model.network

import com.example.garissaestore.model.network.NetworkProduct
import retrofit2.Response
import retrofit2.http.GET

interface ProductsService {
    @GET("products")
    suspend fun getAllProducts(): Response<List<NetworkProduct>>
}