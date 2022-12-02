package com.example.garissaestore.cart

import android.graphics.Canvas
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.airbnb.epoxy.EpoxyTouchHelper
import com.example.garissaestore.MainActivity
import com.example.garissaestore.R
import com.example.garissaestore.databinding.FragmentCartBinding
import com.example.garissaestore.model.ui.UiProductInCart
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.text.NumberFormat
import kotlin.math.max


@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding by lazy {_binding!!}

    private val viewModel: CartFragmentViewModel by viewModels()
    private val currencyFormatter = NumberFormat.getCurrencyInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentCartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val epoxyController = CartFragmentEpoxyController(viewModel, onEmptyStateClicked = {
            //navigate
            (activity as? MainActivity)?.navigateToTab(R.id.productsListFragment)

        })
        binding.rvEpoxy.setController(epoxyController)

        val uiProductInCartFlow = viewModel.uiProductListReducer.reduce(store = viewModel.store).map {uiProducts ->
            uiProducts.filter { it.isInCart }
        }
        combine(
            uiProductInCartFlow,
            viewModel.store.stateFlow.map { it.cartQuantitiesMap }
        ){uiProducts, quatityMap ->
            uiProducts.map {
                UiProductInCart(uiProduct = it, quantity = quatityMap[it.product.id] ?: 1)
            }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){uiProducts ->
            val viewState = if (uiProducts.isEmpty()){
                UiState.Empty
            }else{
                UiState.NonEmpty(uiProducts)
            }
            epoxyController.setData(viewState)
            updateTotalLayout(uiProducts)
            setupSwipeToDelete()
        }
        binding.checkoutButton.setOnClickListener {
            //todo
        }
    }

    private fun updateTotalLayout(uiProductsInCart: List<UiProductInCart>) {
        val totalAmount = uiProductsInCart.sumOf { BigDecimal(it.quantity) * it.uiProduct.product.price }
        val description = "${uiProductsInCart.size} items for ${currencyFormatter.format(totalAmount)}"
        binding.totalDescription.text = description
        binding.checkoutButton.isEnabled = uiProductsInCart.isNotEmpty()
    }

    private fun setupSwipeToDelete() {
        EpoxyTouchHelper
            .initSwiping(binding.rvEpoxy)
            .right()
            .withTarget(CartItemEpoxyModel::class.java)
            .andCallbacks(object : EpoxyTouchHelper.SwipeCallbacks<CartItemEpoxyModel>(){
                override fun onSwipeCompleted(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    position: Int,
                    direction: Int
                ) {
                    model?.let { epoxyModel ->
                        viewModel.viewModelScope.launch {
                            viewModel.store.update {
                                return@update viewModel.uiProductInCartUpdater.update(
                                    productId = epoxyModel.uiProductInCart.uiProduct.product.id,
                                    currentState = it
                                )
                            }
                        }
                    }
                }

                override fun onSwipeProgressChanged(
                    model: CartItemEpoxyModel?,
                    itemView: View?,
                    swipeProgress: Float,
                    canvas: Canvas?
                ) {
                    itemView?.findViewById<View>(R.id.swipeToDismissTextView)?.apply {
                        translationX = max(-itemView.translationX, -measuredWidth.toFloat())
                        alpha = 5f * swipeProgress
                    }
                }

            }
            )    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    sealed interface UiState{
        object Empty: UiState
        data class NonEmpty(val products: List<UiProductInCart>): UiState
    }

}