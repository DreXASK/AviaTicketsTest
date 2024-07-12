package com.drexask.aviatickets.presentation.ui.fragment.weekEnd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drexask.aviatickets.databinding.FragmentWeekendBinding

class WeekendFragment : Fragment() {

    private var _binding: FragmentWeekendBinding? = null
    private val bd: FragmentWeekendBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeekendBinding.inflate(layoutInflater)

        bd.button.setOnClickListener {
            findNavController().popBackStack()
        }

        return bd.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}