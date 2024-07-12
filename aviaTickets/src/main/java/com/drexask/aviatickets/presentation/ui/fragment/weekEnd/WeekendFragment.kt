package com.drexask.aviatickets.presentation.ui.fragment.weekEnd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drexask.aviatickets.databinding.FragmentWeekendBinding
import com.drexask.aviatickets.presentation.utils.extensions.ViewBindingFragment

class WeekendFragment :
    ViewBindingFragment<FragmentWeekendBinding>(FragmentWeekendBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bd.button.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}