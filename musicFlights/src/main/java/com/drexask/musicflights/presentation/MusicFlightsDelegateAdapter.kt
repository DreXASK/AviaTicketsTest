package com.drexask.musicflights.presentation

import com.drexask.musicflights.R
import com.drexask.musicflights.databinding.CardMusicFlightBinding
import com.drexask.musicflights.domain.model.MusicFlightDto
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class MusicFlightsDelegateAdapter:
    ViewBindingDelegateAdapter<MusicFlightDto, CardMusicFlightBinding>(CardMusicFlightBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is MusicFlightDto

    override fun CardMusicFlightBinding.onBind(item: MusicFlightDto) {
        when(item.id) {
            1 -> ivMusician.setImageResource(R.drawable.dora)
            2 -> ivMusician.setImageResource(R.drawable.socrat_lera)
            else -> ivMusician.setImageResource(R.drawable.lampabikt)
        }
        tvTitle.text = item.title
        tvTown.text = item.town
        ivIcon.setImageResource(R.drawable.ic_plane)
        tvPrice.text = "от ${item.price.value} ₽"
    }

        override fun MusicFlightDto.getItemId(): Any = title
}