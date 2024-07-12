package com.drexask.aviatickets.presentation.ui.fragment.allTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.aviatickets.R
import com.drexask.aviatickets.databinding.FragmentAviaTicketsAllTicketsBinding
import com.drexask.aviatickets.presentation.models.bundleModels.SearchPlacesData
import com.drexask.aviatickets.presentation.utils.BUNDLE_DEPARTURE_DATE
import com.drexask.aviatickets.presentation.utils.BUNDLE_PASSENGER_COUNT
import com.drexask.aviatickets.presentation.utils.BUNDLE_SEARCH_PLACES_DATA
import com.drexask.aviatickets.presentation.utils.ToastErrorType
import com.drexask.aviatickets.presentation.utils.parcelable
import com.drexask.aviatickets.presentation.utils.serializable
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@AndroidEntryPoint
class AllTicketsFragment : Fragment() {

    private var _binding: FragmentAviaTicketsAllTicketsBinding? = null
    private val bd: FragmentAviaTicketsAllTicketsBinding get() = _binding!!

    private val viewModel: AllTicketsFragmentViewModel by viewModels()

    private var ticketsAdapter: CompositeDelegateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAviaTicketsAllTicketsBinding.inflate(layoutInflater)

        setupBundleValues()
        setupListeners()
        setupObservers()
        setupRecycler()

        return bd.root
    }

    private fun setupBundleValues() {
        arguments?.apply {
            val places = parcelable<SearchPlacesData>(BUNDLE_SEARCH_PLACES_DATA)
            bd.tvDepartureDestination.text =
                getString(
                    R.string.departure_and_destination,
                    places?.departurePlaceText,
                    places?.destinationPlaceText
                )

            val departureDate = serializable<LocalDate>(BUNDLE_DEPARTURE_DATE)
            val dayOfMonth = departureDate?.dayOfMonth
            val month = departureDate?.month?.getDisplayName(
                TextStyle.FULL,
                Locale.getDefault()
            )
            val passengersCount = getInt(BUNDLE_PASSENGER_COUNT)
            bd.tvDataForSearch.text =
                getString(R.string.data_for_search, dayOfMonth, month, passengersCount)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                toastErrorObserver()
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ticketsObserver()
            }
        }
    }

    private suspend fun toastErrorObserver() {
        viewModel.errorToast.collectLatest { errorType ->
            val errorMessage = when (errorType) {
                ToastErrorType.DOWNLOADING_ERROR -> getString(R.string.downloading_error)
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun ticketsObserver() {
        viewModel.tickets.collectLatest {
            ticketsAdapter?.swapData(it)
        }
    }

    private fun setupListeners() {
        clickGoBack()
        recyclerScrollListener()
    }

    private fun clickGoBack() {
        bd.ivGoBackToMain.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun recyclerScrollListener() {
        bd.rvTickets.setOnScrollChangeListener { _, _, _, _, oldScrollY ->

            if (oldScrollY > 0) {
                bd.cardFloat.visibility = View.VISIBLE
            } else if (oldScrollY < -3) { // < 0 - is scrolling down, < -3 - is scrolling down a little bit faster
                bd.cardFloat.visibility = View.GONE
            }
        }
    }

    private fun setupRecycler() {
        ticketsAdapter = CompositeDelegateAdapter(TicketsDelegateAdapter())

        bd.rvTickets.adapter = ticketsAdapter
        bd.rvTickets.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

}