package com.example.garissaestore.cart


import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import coil.load
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelCartProductItemBinding
import com.example.garissaestore.epoxy.ViewBindingKotlinModel
import com.example.garissaestore.model.ui.UiProductInCart
import java.math.BigDecimal
import java.text.NumberFormat

data class CartItemEpoxyModel(
    val uiProductInCart: UiProductInCart,
    val onFavouriteClicked: () -> Unit,
    private val onDeleteClicked: () -> Unit,
    private val onQuantityChanged: (Int) ->Unit,
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int
): ViewBindingKotlinModel<EpoxyModelCartProductItemBinding>(R.layout.epoxy_model_cart_product_item){

    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun EpoxyModelCartProductItemBinding.bind() {
        swipeToDismissTextView.translationX = 0f

        //setup text
        productTitleTextView.text = uiProductInCart.uiProduct.product.title

        //Fav icon
        val imageRes = if (uiProductInCart.uiProduct.isFavourite){
            R.drawable.ic_fav_filed
        }else {
            R.drawable.ic_fav_not_filled
        }
        favoriteImageView.setIconResource(imageRes)
        favoriteImageView.setOnClickListener { onFavouriteClicked() }

        //deleteItem.setOnClickListener { onDeleteClicked() }

        //Load the image
        productImageView.load(data = uiProductInCart.uiProduct.product.image)

        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, 0, horizontalMargin, 0)
        }
        quantityView.apply {
            quantityTextView.text = uiProductInCart.quantity.toString()
            minusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity - 1) }
            plusImageView.setOnClickListener { onQuantityChanged(uiProductInCart.quantity + 1) }
        }
        val totalPrice = uiProductInCart.uiProduct.product.price * BigDecimal(uiProductInCart.quantity)
        totalProductPriceTextView.text = currencyFormatter.format(totalPrice)
    }

}