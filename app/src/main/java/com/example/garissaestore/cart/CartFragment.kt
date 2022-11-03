package com.example.garissaestore.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import com.example.garissaestore.databinding.FragmentCartBinding
import com.example.garissaestore.model.ui.UiProduct
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding by lazy {_binding!!}

    private val viewModel: CartFragmentViewModel by viewModels()

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

        val epoxyController = CartFragmentEpoxyController()
        binding.rvEpoxy.setController(epoxyController)

        viewModel.uiProductListReducer.reduce(store = viewModel.store).map {uiProducts ->
            uiProducts.filter { it.isInCart }
        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){uiProducts ->
            val viewState = if (uiProducts.isEmpty()){
                UiState.Empty
            }else{
                UiState.NonEmpty(uiProducts)
            }
            epoxyController.setData(viewState)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    sealed interface UiState{
        object Empty: UiState
        data class NonEmpty(val products: List<UiProduct>): UiState
    }

}