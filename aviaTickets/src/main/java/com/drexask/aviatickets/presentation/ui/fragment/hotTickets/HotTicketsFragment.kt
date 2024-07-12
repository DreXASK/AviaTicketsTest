package com.drexask.aviatickets.presentation.ui.fragment.hotTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drexask.aviatickets.databinding.FragmentHotTicketsBinding

class HotTicketsFragment : Fragment() {

    private var _binding: FragmentHotTicketsBinding? = null
    private val bd: FragmentHotTicketsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotTicketsBinding.inflate(layoutInflater)

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