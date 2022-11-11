package com.example.garissaestore.profile.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.garissaestore.model.network.NetworkUser
import com.example.garissaestore.profile.LoginResponse
import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    private val authRepository: AuthRepository
) : ViewModel(){

    fun login(username:String, password: String) = viewModelScope.launch {
        val response: Response<LoginResponse> = authRepository.login(username, password)

        if (response.isSuccessful){
            val donuserResponse: Response<NetworkUser> = authRepository.fetchDon()
            store.update {
                it.copy(user = donuserResponse.body())
            }
            if (donuserResponse.body() == null){
                Log.e("LOGIN", response.errorBody()?.toString() ?: response.message())

            }
        }else{
            Log.e("LOGIN", response.errorBody()?.byteStream()?.bufferedReader()?.readLine() ?: response.message())
        }
    }
}