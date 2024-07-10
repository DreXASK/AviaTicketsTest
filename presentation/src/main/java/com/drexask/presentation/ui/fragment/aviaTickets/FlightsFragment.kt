package com.drexask.presentation.ui.fragment.aviaTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.drexask.presentation.R
import com.drexask.presentation.databinding.FragmentComplexRouteBinding
import com.drexask.presentation.databinding.FragmentFlightsBinding

class FlightsFragment : Fragment() {

    private var _binding: FragmentFlightsBinding? = null
    private val bd: FragmentFlightsBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlightsBinding.inflate(layoutInflater)

        return bd.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}