package com.drexask.aviatickets.presentation.ui.fragment.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.aviatickets.R
import com.drexask.aviatickets.databinding.FragmentAviaTicketsMainBinding
import com.drexask.aviatickets.presentation.models.bundleModels.SearchPlacesData
import com.drexask.aviatickets.presentation.ui.fragment.main.adapters.TicketOfferAdapter
import com.drexask.aviatickets.presentation.ui.fragment.main.adapters.MusicFlightsDelegateAdapter
import com.drexask.aviatickets.presentation.utils.BUNDLE_DEPARTURE_DATE
import com.drexask.aviatickets.presentation.utils.BUNDLE_PASSENGER_COUNT
import com.drexask.aviatickets.presentation.utils.BUNDLE_SEARCH_PLACES_DATA
import com.drexask.aviatickets.presentation.utils.DEPARTURE_CACHE
import com.drexask.aviatickets.presentation.utils.SpaceItemDecoration
import com.drexask.aviatickets.presentation.utils.ToastErrorType
import com.drexask.aviatickets.presentation.utils.extensions.ViewBindingFragment
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@AndroidEntryPoint
class MainFragment :
    ViewBindingFragment<FragmentAviaTicketsMainBinding>(FragmentAviaTicketsMainBinding::inflate) {

    private val viewModel: MainFragmentViewModel by activityViewModels()
    private var musicFlightAdapter: CompositeDelegateAdapter? = null
    private var ticketOfferAdapter: CompositeDelegateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreCachedValues()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclers()
        setupObservers()
        setupListeners()
        setupTextViews()
        changeBackButtonBehavior()
    }

    private fun restoreCachedValues() {
        val sharedPrefs = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        sharedPrefs?.let { prefs ->
            val departureCachedText = prefs.getString(DEPARTURE_CACHE, "")
            viewModel.searchPlacesData.value = viewModel.searchPlacesData.value.copy(
                departurePlaceText = departureCachedText
            )
        }
    }

    private fun setupRecyclers() {
        setupMusicFlightsRecyclerView()
        setupTicketOffersRecyclerView()
    }

    private fun setupMusicFlightsRecyclerView() {
        musicFlightAdapter = CompositeDelegateAdapter(MusicFlightsDelegateAdapter())

        bd.rvMusicFlights.adapter = musicFlightAdapter
        bd.rvMusicFlights.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        bd.rvMusicFlights.addItemDecoration(SpaceItemDecoration(100))
    }

    private fun setupTicketOffersRecyclerView() {
        ticketOfferAdapter = CompositeDelegateAdapter(TicketOfferAdapter())

        bd.rvTicketOffersDestinationSelected.adapter = ticketOfferAdapter
        bd.rvTicketOffersDestinationSelected.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupObservers() {

        // Common states -----------------------------------------
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTextsObserver()
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                screenStateObserver()
            }
        }

        // DEFAULT state -----------------------------------------
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                musicFlightsObserver()
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                toastErrorObserver()
            }
        }

        // DESTINATION_SELECTED state ----------------------------
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                dateOfFlightObserver()
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ticketOffersObserver()
            }
        }
    }

    private suspend fun musicFlightsObserver() {
        viewModel.musicFlights.collectLatest {
            musicFlightAdapter?.swapData(it)
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

    private suspend fun editTextsObserver() {
        viewModel.searchPlacesData.collectLatest {
            it.departurePlaceText?.let { departureText ->
                bd.etDeparture.setText(departureText)
                bd.tvDepartureDestinationSelected.text = departureText
            }
            it.destinationPlaceText?.let { destinationText ->
                bd.etDestination.setText(destinationText)
                bd.tvDestinationDestinationSelected.text = destinationText
            }
        }
    }

    private suspend fun screenStateObserver() {
        viewModel.screenState.collectLatest {
            when (it) {
                MainFragmentViewModel.ScreenState.DEFAULT -> {
                    bd.constraintLayoutStateDefault.visibility = View.VISIBLE
                    bd.constraintLayoutStateDestinationSelected.visibility = View.GONE
                }

                MainFragmentViewModel.ScreenState.DESTINATION_SELECTED -> {
                    bd.constraintLayoutStateDefault.visibility = View.GONE
                    bd.constraintLayoutStateDestinationSelected.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun changeBackButtonBehavior() {
        val onBackPressedDispatcher = activity?.onBackPressedDispatcher
        onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            if (viewModel.screenState.value == MainFragmentViewModel.ScreenState.DESTINATION_SELECTED) {
                viewModel.clearDestination()
            } else {
                remove() // remove this callBack so that user can exit the app
                onBackPressedDispatcher.onBackPressed() // press back button one more time after removing the callBack
            }
        }
    }

    private suspend fun dateOfFlightObserver() {
        viewModel.searchDateOfFlightState.collectLatest {
            val spannableString = getSpannableStringOfDate(it)
            bd.btnSelectDateDestinationSelected.text = spannableString
        }
    }

    private suspend fun ticketOffersObserver() {
        viewModel.ticketOffers.collectLatest {
            ticketOfferAdapter?.swapData(it)
        }
    }

    private fun setupListeners() {
        // DEFAULT state
        clickOnDestination()
        // DESTINATION_SELECTED state ----------------------------
        clickReverseDepartureDestination()
        clickClearDestination()
        clickGoBack()
        clickSelectBackFlightDate()
        clickSelectFlightDate()
        clickViewAllTickets()
    }

    private fun clickOnDestination() {
        bd.etDestination.setOnClickListener {
            saveEditTextsToState()
            showDestinationDialog()
        }
    }

    private fun clickReverseDepartureDestination() {
        bd.ivReverseDestinationSelected.setOnClickListener {
            viewModel.apply {
                searchPlacesData.value = searchPlacesData.value.copy(
                    departurePlaceText = searchPlacesData.value.destinationPlaceText,
                    destinationPlaceText = searchPlacesData.value.departurePlaceText
                )
            }
        }
    }

    private fun clickClearDestination() {
        bd.ivClearTextFieldDestinationSelected.setOnClickListener {
            viewModel.clearDestination()
        }
    }

    private fun clickGoBack() {
        bd.ivGoBack.setOnClickListener {
            viewModel.clearDestination()
        }
    }

    private fun clickSelectBackFlightDate() {
        bd.btnTicketToBackDestinationSelected.setOnClickListener {
            context?.let { context ->
                val datePickerDialog = DatePickerDialog(context)
                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    Toast.makeText(
                        context,
                        "Выбран обратный билет. Дата - ${dayOfMonth}.${month}.${year}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                datePickerDialog.show()
            }

        }
    }

    private fun clickSelectFlightDate() {
        bd.btnSelectDateDestinationSelected.setOnClickListener {
            context?.let { context ->
                val datePickerDialog = DatePickerDialog(context)
                datePickerDialog.setOnDateSetListener { _, year, month, dayOfMonth ->
                    viewModel.searchDateOfFlightState.value = LocalDate.of(
                        year,
                        month + 1,
                        dayOfMonth
                    ) // month is starting from 0 in datePickerDialog
                }
                datePickerDialog.show()
            }
        }
    }

    private fun clickViewAllTickets() {
        bd.btnAllTicketsDestinationSelected.setOnClickListener {
            val bundle = Bundle().apply {
                putParcelable(BUNDLE_SEARCH_PLACES_DATA, viewModel.searchPlacesData.value)
                putSerializable(BUNDLE_DEPARTURE_DATE, viewModel.searchDateOfFlightState.value)
                putInt(BUNDLE_PASSENGER_COUNT, 1)
            }
            findNavController().navigate(R.id.allTicketsFragment, bundle)
        }
    }

    private fun setupTextViews() {
        // DESTINATION_SELECTED state ----------------------------
        setupFlightDateTextView()
    }

    private fun setupFlightDateTextView() {
        val currentDate = LocalDate.now()
        val spannableString = getSpannableStringOfDate(currentDate)
        bd.btnSelectDateDestinationSelected.text = spannableString
    }

    private fun getSpannableStringOfDate(date: LocalDate): SpannableString {
        val locale = Locale.getDefault()

        val monthName = date.month.getDisplayName(
            TextStyle.SHORT,
            locale
        )
        val dayOfWeekName = date.dayOfWeek.getDisplayName(
            TextStyle.SHORT,
            locale
        )
        val dateString = "${date.dayOfMonth} $monthName, $dayOfWeekName"
        val dateStringWithoutPoint = dateString.replace(".", "")

        val spannableString = SpannableString(dateStringWithoutPoint)

        val indexOfComma = spannableString.indexOf(",")
        context?.let { context ->
            spannableString.setSpan(
                ForegroundColorSpan(
                    ContextCompat.getColor(
                        context,
                        com.drexask.core.R.color.grey_6
                    )
                ),
                indexOfComma,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    private fun saveEditTextsToState() {
        viewModel.searchPlacesData.value = SearchPlacesData(
            departurePlaceText = bd.etDeparture.text.toString(),
            destinationPlaceText = bd.etDestination.text.toString()
        )
    }

    private fun showDestinationDialog() {
        val dialog = DestinationBottomSheetFragment(
            R.layout.fragment_avia_tickets_destination_bottom_sheet
        )
        dialog.show(childFragmentManager, null)
    }
}