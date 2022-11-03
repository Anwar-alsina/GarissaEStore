package com.example.garissaestore.epoxy

import android.view.ViewGroup
import androidx.annotation.Dimension
import androidx.core.view.updateLayoutParams
import com.example.garissaestore.R
import com.example.garissaestore.databinding.EpoxyModelDividerBinding

data class DividerEpoxyModel(
    @Dimension(unit = Dimension.PX) private val horizontalMargin: Int = 0,
    @Dimension(unit = Dimension.PX) private val verticalMargin: Int = 0
) : ViewBindingKotlinModel<EpoxyModelDividerBinding>(R.layout.epoxy_model_divider) {

    override fun EpoxyModelDividerBinding.bind() {
        root.updateLayoutParams<ViewGroup.MarginLayoutParams> {
            setMargins(horizontalMargin, verticalMargin, horizontalMargin, verticalMargin)
        }
    }
}