package com.example.garissaestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.garissaestore.databinding.ActivityMainBinding
import com.example.garissaestore.epoxy.UiProductEpoxyController
import com.example.garissaestore.model.ui.UiProduct
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by lazy {
        ViewModelProvider(this)[MainActivityViewModel::class.java]
    }

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val controller = UiProductEpoxyController(viewModel)
        binding.epoxyRecyclerView.setController(controller)
        controller.setData(emptyList())

        combine(
            viewModel.store.stateFlow.map { it.products},
            viewModel.store.stateFlow.map { it.favouriteProductIds }
        ){lisOfProducts, setOfFavouriteIds ->
            lisOfProducts.map{ products ->
                UiProduct(product = products, isFavourite = setOfFavouriteIds.contains(products.id) )
            }
        }.distinctUntilChanged().asLiveData().observe(this){uiProduct ->
            controller.setData(uiProduct)
        }
        viewModel.refreshProducts()
    }
}


