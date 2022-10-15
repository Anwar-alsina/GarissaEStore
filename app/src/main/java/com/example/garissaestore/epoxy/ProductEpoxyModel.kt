package com.example.garissaestore.epoxy

import androidx.core.view.isGone
import androidx.core.view.isVisible
import coil.load
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelProductItemBinding
import com.example.garissaestore.model.domain.Product

data class ProductEpoxyModel (
    val product: Product
        ): ViewBindingKotlinModel<EpoxyModelProductItemBinding>(R.layout.epoxy_model_product_item){
    override fun EpoxyModelProductItemBinding.bind() {
        //set text
        productTitleTextView.text = product.title
        productDescriptionTextView.text = product.description
        productCategoryTextView.text = product.category
        productPriceTextView.text = product.price.toPlainString()

        //load image
        productImageViewLoadingProgressBar.isVisible = true
        productImageView.load(
            data = product.image
        ){
            listener{request, result ->
            productImageViewLoadingProgressBar.isGone = true
            }
        }
    }

}