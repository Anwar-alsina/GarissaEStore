package com.example.garissaestore.cart

import androidx.lifecycle.ViewModel
import com.example.garissaestore.redux.ApplicationState
import com.example.garissaestore.redux.Store
import com.example.garissaestore.redux.reducer.UiProductListReducer
import com.example.garissaestore.redux.updater.UiProductFavouriteUpdater
import com.example.garissaestore.redux.updater.UiProductInCartUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartFragmentViewModel @Inject constructor(
    val store: Store<ApplicationState>,
    val uiProductListReducer: UiProductListReducer,
    val uiProductFavouriteUpdater: UiProductFavouriteUpdater,
    val uiProductInCartUpdater: UiProductInCartUpdater
) : ViewModel(){
    //todo
}