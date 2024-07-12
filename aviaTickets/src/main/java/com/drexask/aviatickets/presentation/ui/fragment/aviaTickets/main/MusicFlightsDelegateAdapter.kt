package com.drexask.presentation.ui.fragment.aviaTickets.main

import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardMusicFlightBinding
import com.drexask.presentation.models.MusicFlightUi
import com.drexask.presentation.utils.applyPriceDecorator
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class MusicFlightsDelegateAdapter:
    ViewBindingDelegateAdapter<MusicFlightUi, CardMusicFlightBinding>(CardMusicFlightBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is MusicFlightUi

    override fun CardMusicFlightBinding.onBind(item: MusicFlightUi) {
        when(item.id) {
            1 -> ivMusician.setImageResource(R.drawable.img_dora)
            2 -> ivMusician.setImageResource(R.drawable.img_socrat_lera)
            else -> ivMusician.setImageResource(R.drawable.img_lampabikt)
        }
        tvTitle.text = item.title
        tvTown.text = item.town
        tvPrice.text = root.context.getString(R.string.price_text_from, item.priceUi.value.toString().applyPriceDecorator())
    }

        override fun MusicFlightUi.getItemId(): Any = id
}