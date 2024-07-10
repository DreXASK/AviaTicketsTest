package com.drexask.presentation.ui.fragment.aviaTickets.main

import android.os.Bundle
import androidx.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.presentation.R
import com.drexask.presentation.databinding.FragmentAviaTicketsMainBinding
import com.drexask.presentation.utils.SpaceItemDecoration
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val DEPARTURE_CACHE = "DEPARTURE_CACHE"

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentAviaTicketsMainBinding? = null
    private val bd: FragmentAviaTicketsMainBinding get() = _binding!!

    private val viewModel: MainFragmentViewModel by viewModels()
    private var recyclerAdapter: CompositeDelegateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAviaTicketsMainBinding.inflate(layoutInflater)

        setupRecyclerView()
        setupObservers()
        setupListeners()
        setupCachedValues()

        return bd.root
    }

    private fun setupCachedValues() {
        val sharedPrefs = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        sharedPrefs?.let { prefs ->
            bd.etDeparture.setText(prefs.getString(DEPARTURE_CACHE, ""))
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                musicFlightsObserver()
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                toastObserver()
            }
        }
    }

    private suspend fun musicFlightsObserver() {
        viewModel.musicFlights.collectLatest {
            recyclerAdapter?.swapData(it)
        }
    }

    private suspend fun toastObserver() {
        viewModel.errorToastFlow.collectLatest { errorType ->
            val errorMessage = when (errorType) {
                MainFragmentViewModel.Error.DOWNLOADING_ERROR -> getString(R.string.downloading_error)
            }

            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private fun setupRecyclerView() {
        recyclerAdapter = CompositeDelegateAdapter(MusicFlightsDelegateAdapter())

        bd.rvMusicFlights.adapter = recyclerAdapter
        bd.rvMusicFlights.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        bd.rvMusicFlights.addItemDecoration(SpaceItemDecoration(100))
    }

    private fun setupListeners() {
        destinationOnClick()
    }

    private fun destinationOnClick() {
        bd.etDestination.setOnClickListener {
            cacheDepartureFieldValue()
            showDestinationDialog()
        }
    }

    private fun cacheDepartureFieldValue() {
        if (bd.etDeparture.text.isNullOrBlank())
            return

        val sharedPrefs = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }

        sharedPrefs?.let { prefs ->
            val editor = prefs.edit()
            editor.putString(DEPARTURE_CACHE, bd.etDeparture.text.toString())
            editor.apply()
        }
    }

    private fun showDestinationDialog() {
        val dialog = DestinationBottomSheetFragment(
            bd.etDeparture.text.toString(),
            R.layout.fragment_avia_tickets_destination_bottom_sheet
        )
        dialog.show(childFragmentManager, null)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}