package com.example.garissaestore.profile.auth

import com.example.garissaestore.model.network.NetworkUser
import com.example.garissaestore.model.network.post.LoginPostBody
import com.example.garissaestore.profile.LoginResponse
import retrofit2.Response
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authService: AuthService
) {
    suspend fun login(username: String, password:String):Response<LoginResponse>{
        return authService.login(LoginPostBody(username, password))
    }

    suspend fun fetchDon(): Response<NetworkUser>{
        return authService.fetchUser(userId = 4)
    }
}