package com.drexask.presentation.ui.fragment.aviaTickets.main

import android.content.res.ColorStateList
import android.graphics.Color
import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardDirectFlightBinding
import com.drexask.presentation.models.DirectFlightUi
import com.drexask.presentation.utils.PositionViewBindingDelegateAdapter
import com.drexask.presentation.utils.applyPriceDecorator

class DirectFlightDelegateAdapter:
    PositionViewBindingDelegateAdapter<DirectFlightUi, CardDirectFlightBinding>(CardDirectFlightBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is DirectFlightUi

    override fun CardDirectFlightBinding.onBind(item: DirectFlightUi, position: Int) {
        when(position) {
            0 -> ivCircle.imageTintList = ColorStateList.valueOf(Color.RED)
            1 -> ivCircle.imageTintList = ColorStateList.valueOf(Color.BLUE)
            else -> ivCircle.imageTintList = ColorStateList.valueOf(Color.WHITE)
        }
        tvCompanyName.text = item.title
        tvDepartureTimes.text = item.timeRange.joinToString("  ")
        tvPrice.text = root.context.resources.getString(R.string.price_text, item.priceUi.value.toString().applyPriceDecorator())
    }

    override fun DirectFlightUi.getItemId(): Any = title
}
