package com.example.garissaestore.model.network

data class NetworkUser(
    val address: Address,
    val email: String,
    val id: Int,
    val name: Name,
    val password: String,
    val phone: String,
    val username: String
)