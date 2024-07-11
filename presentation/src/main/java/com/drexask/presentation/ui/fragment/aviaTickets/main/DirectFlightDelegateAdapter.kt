package com.drexask.presentation.ui.fragment.aviaTickets.main

import android.content.res.ColorStateList
import android.graphics.Color
import com.drexask.domain.model.DirectFlight
import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardDirectFlightBinding
import com.drexask.presentation.utils.PositionViewBindingDelegateAdapter
import com.drexask.presentation.utils.applyPriceDecorator
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class DirectFlightDelegateAdapter():
    PositionViewBindingDelegateAdapter<DirectFlight, CardDirectFlightBinding>(CardDirectFlightBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is DirectFlight

    override fun CardDirectFlightBinding.onBind(item: DirectFlight, position: Int) {
        when(position) {
            0 -> ivCircle.imageTintList = ColorStateList.valueOf(Color.RED)
            1 -> ivCircle.imageTintList = ColorStateList.valueOf(Color.BLUE)
            else -> ivCircle.imageTintList = ColorStateList.valueOf(Color.WHITE)
        }
        tvCompanyName.text = item.title
        tvDepartureTimes.text = item.timeRange.joinToString("  ")
        tvPrice.text = root.context.resources.getString(R.string.price_text, item.price.value.toString().applyPriceDecorator())
    }

    override fun DirectFlight.getItemId(): Any = title
}
