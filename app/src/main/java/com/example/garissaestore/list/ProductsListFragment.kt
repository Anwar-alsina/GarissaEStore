package com.example.garissaestore.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.garissaestore.databinding.FragmentProductsListBinding
import com.example.garissaestore.epoxy.UiProductEpoxyController
import com.example.garissaestore.model.ui.ProductListFragmentUiState
import com.example.garissaestore.model.ui.UiFilter
import com.example.garissaestore.model.ui.UiProduct
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@AndroidEntryPoint
class ProductsListFragment : Fragment() {
    private var _binding: FragmentProductsListBinding? = null
    private val binding by lazy { _binding!! }

    private val viewModel: ProductListViewModel by viewModels()

    @Inject
    lateinit var uiStateGenerator: ProductsListFragmentUiStateGenerator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProductsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val controller = UiProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)
        //controller.setData(ProductListFragmentUiState.Loading)

        combine(
            viewModel.uiProductListReducer.reduce(viewModel.store),
            viewModel.store.stateFlow.map { it.productFilterInfo },
        ){ uiProducts, productFilterInfo ->
            uiStateGenerator.generate(uiProducts,productFilterInfo)

        }.distinctUntilChanged().asLiveData().observe(viewLifecycleOwner){ uiProduct ->
            controller.setData(uiProduct)
        }
        viewModel.refreshProducts()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

