package com.drexask.aviatickets.presentation.utils.extensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

typealias Bind<T> = (View) -> T

abstract class ViewBindingBottomSheetDialogFragment<T : ViewBinding>(
    private val bind: Bind<T>,
    @LayoutRes layoutRes: Int
) : BottomSheetDialogFragment(layoutRes) {

    private var _binding: T? = null
    val bd: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        _binding = view?.let { bind.invoke(it) }
        return bd.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}