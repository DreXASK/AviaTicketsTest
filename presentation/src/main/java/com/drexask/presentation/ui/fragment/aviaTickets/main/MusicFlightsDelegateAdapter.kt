package com.drexask.presentation.ui.fragment.aviaTickets.main


import com.drexask.domain.model.MusicFlight
import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardMusicFlightBinding
import com.drexask.presentation.utils.applyPriceDecorator
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class MusicFlightsDelegateAdapter:
    ViewBindingDelegateAdapter<MusicFlight, CardMusicFlightBinding>(CardMusicFlightBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is MusicFlight

    override fun CardMusicFlightBinding.onBind(item: MusicFlight) {
        when(item.id) {
            1 -> ivMusician.setImageResource(R.drawable.img_dora)
            2 -> ivMusician.setImageResource(R.drawable.img_socrat_lera)
            else -> ivMusician.setImageResource(R.drawable.img_lampabikt)
        }
        tvTitle.text = item.title
        tvTown.text = item.town
        ivIcon.setImageResource(R.drawable.ic_plane)
        tvPrice.text = root.context.getString(R.string.price_text_from, item.price.value.toString().applyPriceDecorator())
    }

        override fun MusicFlight.getItemId(): Any = id
}