package com.drexask.presentation.ui.fragment.aviaTickets.placeholders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.drexask.presentation.R
import com.drexask.presentation.databinding.FragmentAviaTicketsMainBinding
import com.drexask.presentation.databinding.FragmentComplexRouteBinding

class ComplexRouteFragment : Fragment() {

    private var _binding: FragmentComplexRouteBinding? = null
    private val bd: FragmentComplexRouteBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentComplexRouteBinding.inflate(layoutInflater)

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