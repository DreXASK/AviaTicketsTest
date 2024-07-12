package com.drexask.aviatickets.presentation.utils.extensions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class ViewBindingFragment<T: ViewBinding>(private val inflate: Inflate<T>) : Fragment() {

    private var _binding: T? = null
    val bd: T get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflate.invoke(inflater, container, false)
        return bd.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}