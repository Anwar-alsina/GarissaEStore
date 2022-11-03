package com.example.garissaestore.list

import androidx.core.content.ContextCompat
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelProductFilterBinding
import com.example.garissaestore.epoxy.ViewBindingKotlinModel
import com.example.garissaestore.model.domain.Filter
import com.example.garissaestore.model.ui.UiFilter

data class UiProductFilterEpoxyModel(
    val uiFilter: UiFilter,
    val onFilterSelected: (Filter) -> Unit
): ViewBindingKotlinModel<EpoxyModelProductFilterBinding>(R.layout.epoxy_model_product_filter){
    override fun EpoxyModelProductFilterBinding.bind() {
        root.setOnClickListener { onFilterSelected(uiFilter.filter) }
        tvFilter.text = uiFilter.filter.displayText

        val cardBackgroundResId = if (uiFilter.isSelected){
            R.color.purple_500
        }else{
            R.color.purple_200
        }
        root.setCardBackgroundColor(ContextCompat.getColor(root.context,cardBackgroundResId))
    }

}

