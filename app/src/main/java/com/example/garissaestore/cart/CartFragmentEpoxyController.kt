package com.example.garissaestore.cart

import com.airbnb.epoxy.TypedEpoxyController
import com.example.garissaestore.epoxy.DividerEpoxyModel
import com.example.garissaestore.epoxy.VerticalSpaceEpoxyModel
import com.example.garissaestore.extensions.toPx

class CartFragmentEpoxyController: TypedEpoxyController<CartFragment.UiState>() {

    override fun buildModels(data: CartFragment.UiState?) {
        when (data){
            null, is CartFragment.UiState.Empty -> {
                CartEmptyEpoxyModel(onClick = {
                    //todo
                }).id("empty_state").addTo(this)
            }
            is CartFragment.UiState.NonEmpty -> {
                data.products.forEachIndexed{index, uiProduct ->
                    addVerticalStyling(index)
                    CartItemEpoxyModel(
                        uiProduct = uiProduct,
                        horizontalMargin = 16.toPx(),
                        onFavouriteClicked = {
                            //todo
                        },
                        onDeleteClicked = {
                            //todo
                        }
                    ).id(uiProduct.product.id).addTo(this)
                }
            }
        }
    }

    private fun addVerticalStyling(index: Int) {
        VerticalSpaceEpoxyModel(8.toPx()).id("top_space_$index").addTo(this)

        if (index != 0) {
            DividerEpoxyModel(horizontalMargin = 16.toPx()).id("divider_$index").addTo(this)
        }

        VerticalSpaceEpoxyModel(8.toPx()).id("bottom_space_$index").addTo(this)
    }

}