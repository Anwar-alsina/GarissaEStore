package com.example.garissaestore.epoxy

import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import coil.load
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelProductItemBinding
import com.example.garissaestore.model.ui.UiProduct
import java.text.NumberFormat
import kotlin.reflect.KFunction1

data class UiProductEpoxyModel(
    val uiProduct: UiProduct?,
    val onFavouriteIconClicked: (Int) -> Unit,
    val onUiProductClicked: (Int) -> Unit
): ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item){

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelProductItemBinding.bind() {
        shimmerLayout.isVisible = uiProduct == null
        cardView.isInvisible = uiProduct == null

        uiProduct?.let {uiProduct ->
            shimmerLayout.stopShimmer()

        //set text
        productTitleTextView.text = uiProduct.product.title
        productDescriptionTextView.text = uiProduct.product.description
        productCategoryTextView.text = uiProduct.product.category
        productPriceTextView.text = currencyFormatter.format(uiProduct.product.price)

         //Expanded state
         productDescriptionTextView.isVisible = uiProduct.isExpanded
         root.setOnClickListener { onUiProductClicked(uiProduct.product.id) }

        //Favourite icon
        val imageRes = if (uiProduct.isFavourite){
            R.drawable.ic_fav_filed
        }else{
            R.drawable.ic_fav_not_filled
        }
            favoriteImageView.setIconResource(imageRes)
            favoriteImageView.setOnClickListener {
                onFavouriteIconClicked(uiProduct.product.id)
            }

        //load image
        productImageViewLoadingProgressBar.isVisible = true
        productImageView.load(
            data = uiProduct.product.image
        ){
            listener{request, result ->
            productImageViewLoadingProgressBar.isGone = true
            }
        }
    }?: shimmerLayout.startShimmer()

}
        }