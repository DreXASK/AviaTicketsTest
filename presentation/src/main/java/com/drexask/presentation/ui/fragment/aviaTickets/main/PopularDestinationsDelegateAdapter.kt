package com.drexask.presentation.ui.fragment.aviaTickets.main

import com.drexask.domain.model.PopularDestination
import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardPopularDestinationBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class PopularDestinationsDelegateAdapter(private val clickListener: (String) -> Unit):
    ViewBindingDelegateAdapter<PopularDestination,
            CardPopularDestinationBinding>(CardPopularDestinationBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is PopularDestination

    override fun CardPopularDestinationBinding.onBind(item: PopularDestination) {
        when (item.id) {
            1 -> ivDestination.setImageResource(R.drawable.img_stambul)
            2 -> ivDestination.setImageResource(R.drawable.img_sochi)
            3 -> ivDestination.setImageResource(R.drawable.img_phuket)
        }
        layout.setOnClickListener {
            clickListener(item.city)
        }
        tvCity.text = item.city
        tvInfo.text = item.info
    }

    override fun PopularDestination.getItemId(): Any = id
}