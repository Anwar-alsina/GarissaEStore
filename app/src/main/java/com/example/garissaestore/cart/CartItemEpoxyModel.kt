package com.example.garissaestore.cart

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelCartProductItemBinding
import com.example.garissaestore.epoxy.ViewBindingKotlinModel
import com.example.garissaestore.model.ui.UiProduct

data class CartItemEpoxyModel(
    private val uiProduct: UiProduct,
    private val onFavouriteClicked: () -> Unit,
    private val onDeleteClicked: () -> Unit,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int
): ViewBindingKotlinModel<EpoxyModelCartProductItemBinding>(R.layout.epoxy_model_cart_product_item){
    override fun EpoxyModelCartProductItemBinding.bind() {
        //setup text
        productTitleTextView.text = uiProduct.product.title

        //Fav icon
        val imageRes = if (uiProduct.isFavourite){
            R.drawable.ic_fav_filed
        }else {
            R.drawable.ic_fav_not_filled
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener { onFavouriteClicked }

        //icDelete.setOnClickListener { onDeleteClicked }

        //Load the image
        productImageView.load(data = uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }
    }

}