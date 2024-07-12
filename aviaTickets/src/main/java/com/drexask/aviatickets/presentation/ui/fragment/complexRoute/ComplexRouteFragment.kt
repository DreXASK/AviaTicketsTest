package com.drexask.aviatickets.presentation.ui.fragment.complexRoute

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.drexask.aviatickets.databinding.FragmentComplexRouteBinding
import com.drexask.aviatickets.presentation.utils.extensions.ViewBindingFragment

class ComplexRouteFragment :
    ViewBindingFragment<FragmentComplexRouteBinding>(FragmentComplexRouteBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bd.button.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}