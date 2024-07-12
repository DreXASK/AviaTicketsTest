package com.drexask.aviatickets.presentation.ui.fragment.hotTickets

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.drexask.aviatickets.databinding.FragmentHotTicketsBinding
import com.drexask.aviatickets.presentation.utils.extensions.ViewBindingFragment

class HotTicketsFragment :
    ViewBindingFragment<FragmentHotTicketsBinding>(FragmentHotTicketsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bd.button.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}