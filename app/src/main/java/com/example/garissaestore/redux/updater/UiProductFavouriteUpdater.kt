package com.example.garissaestore.redux.updater

import com.example.garissaestore.redux.ApplicationState
import javax.inject.Inject

class UiProductFavouriteUpdater @Inject constructor() {
    fun onProductFavourite(productId: Int, currentState: ApplicationState): ApplicationState{

            val currentFavouriteIds = currentState.favouriteProductIds
            val newFavouriteIds = if (currentFavouriteIds.contains(productId)) {
                currentFavouriteIds - productId
            } else{
                currentFavouriteIds + productId
            }
            return currentState.copy(favouriteProductIds = newFavouriteIds)
        }
    }

