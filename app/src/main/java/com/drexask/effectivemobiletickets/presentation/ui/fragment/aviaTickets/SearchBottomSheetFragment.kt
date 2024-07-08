package com.drexask.effectivemobiletickets.presentation.ui.fragment.aviaTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.drexask.effectivemobiletickets.databinding.FragmentSearchBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SearchBottomSheetFragment(@LayoutRes layoutRes: Int) :
    BottomSheetDialogFragment(layoutRes) {

    private var _binding: FragmentSearchBottomSheetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        _binding = view?.let { FragmentSearchBottomSheetBinding.bind(it) }

        setupListeners()

        return view
    }

    private fun setupListeners() {
        clickFileNames()
    }

    private fun clickFileNames() {
        binding.chipFileNames.setOnCheckedChangeListener { _, isChecked ->
            mainActivitySharedData.useFileNamesLD.value = isChecked
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

