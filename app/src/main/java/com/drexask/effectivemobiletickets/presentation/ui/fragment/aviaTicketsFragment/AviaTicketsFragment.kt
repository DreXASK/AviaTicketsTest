package com.drexask.effectivemobiletickets.presentation.ui.fragment.aviaTicketsFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.effectivemobiletickets.databinding.FragmentAviaTicketsBinding
import com.drexask.core.presentation.utils.SpaceItemDecoration
import com.drexask.core.presentation.utils.applyPriceDecorator
import com.drexask.musicflights.presentation.MusicFlightsDelegateAdapter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class AviaTicketsFragment: Fragment() {

    private var _binding: FragmentAviaTicketsBinding? = null
    private val bd: FragmentAviaTicketsBinding
        get() = _binding!!

    private val viewModel: AviaTicketsFragmentViewModel by viewModels()
    private var adapter: CompositeDelegateAdapter? = CompositeDelegateAdapter(MusicFlightsDelegateAdapter())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAviaTicketsBinding.inflate(layoutInflater)

        setupRecyclerView()
        initObservers()

        return bd.root
    }

    private fun initObservers() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                musicFlightsObserver()
            }
        }
    }

    private suspend fun musicFlightsObserver() {
        viewModel.musicFlights.collect {
            adapter?.swapData(it)
        }
    }

    private fun setupRecyclerView() {
        bd.musicFlightsRecycler.adapter = adapter
        bd.musicFlightsRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        bd.musicFlightsRecycler.addItemDecoration(SpaceItemDecoration(100))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        adapter = null
    }

}