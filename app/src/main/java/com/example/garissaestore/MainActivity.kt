package com.example.garissaestore

import android.location.GnssAntennaInfo.Listener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import coil.load
import com.example.garissaestore.databinding.ActivityMainBinding
import com.example.garissaestore.epoxy.ProductEpoxyController
import com.example.garissaestore.model.domain.Product
import com.example.garissaestore.model.mapper.ProductMapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var productsService: ProductsService

    @Inject
    lateinit var productMapper: ProductMapper

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val controller = ProductEpoxyController()
        binding.epoxyRecyclerView.setController(controller)

        lifecycleScope.launchWhenStarted {
            val response = productsService.getAllProducts()
            val domainProducts :List<Product> = response.body()?.map{
                productMapper.buildFrom(it)
            }?: emptyList()
            controller.setData(domainProducts)
            Log.i("DATA", response.body()!!.toString())
        }

    }
}


