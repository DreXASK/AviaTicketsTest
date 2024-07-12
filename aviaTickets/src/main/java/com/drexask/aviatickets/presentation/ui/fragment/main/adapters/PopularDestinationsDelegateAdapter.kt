package com.drexask.aviatickets.presentation.ui.fragment.main.adapters

import com.drexask.aviatickets.R
import com.drexask.aviatickets.databinding.CardPopularDestinationBinding
import com.drexask.aviatickets.presentation.models.PopularDestinationUi
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class PopularDestinationsDelegateAdapter(private val clickListener: (String) -> Unit):
    ViewBindingDelegateAdapter<PopularDestinationUi,
            CardPopularDestinationBinding>(CardPopularDestinationBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is PopularDestinationUi

    override fun CardPopularDestinationBinding.onBind(item: PopularDestinationUi) {
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

    override fun PopularDestinationUi.getItemId(): Any = id
}