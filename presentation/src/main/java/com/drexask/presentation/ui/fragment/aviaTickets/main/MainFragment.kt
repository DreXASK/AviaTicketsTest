package com.drexask.presentation.ui.fragment.aviaTickets.main

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.presentation.R
import com.drexask.presentation.databinding.FragmentAviaTicketsMainBinding
import com.drexask.presentation.utils.DEPARTURE_CACHE
import com.drexask.presentation.utils.SpaceItemDecoration
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentAviaTicketsMainBinding? = null
    private val bd: FragmentAviaTicketsMainBinding get() = _binding!!

    private val viewModel: MainFragmentViewModel by activityViewModels()
    private var musicFlightAdapter: CompositeDelegateAdapter? = null
    private var directFlightAdapter: CompositeDelegateAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        restoreCachedValues()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAviaTicketsMainBinding.inflate(layoutInflater)

        setupRecyclers()
        setupObservers()
        setupListeners()
        setupTextViews()

        return bd.root
    }

    private fun restoreCachedValues() {
        val sharedPrefs = context?.let { PreferenceManager.getDefaultSharedPreferences(it) }
        sharedPrefs?.let { prefs ->
            val departureCachedText = prefs.getString(DEPARTURE_CACHE, "")
            viewModel.editTextsDataState.value = viewModel.editTextsDataState.value.copy(
                departurePlaceText = departureCachedText
            )
        }
    }

    private fun setupRecyclers() {
        setupMusicFlightsRecyclerView()
        setupDirectFlightsRecyclerView()
    }

    private fun setupMusicFlightsRecyclerView() {
        musicFlightAdapter = CompositeDelegateAdapter(MusicFlightsDelegateAdapter())

        bd.rvMusicFlights.adapter = musicFlightAdapter
        bd.rvMusicFlights.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        bd.rvMusicFlights.addItemDecoration(SpaceItemDecoration(100))
    }

    private fun setupDirectFlightsRecyclerView() {
        directFlightAdapter = CompositeDelegateAdapter(DirectFlightDelegateAdapter())

        bd.rvDirectFlightsDestinationSelected.adapter = directFlightAdapter
        bd.rvDirectFlightsDestinationSelected.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

    private fun setupObservers() {

        // Common states -----------------------------------------
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTextsObserver()
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
                toastObserver()
            }
        }
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                screenStateObserver()
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
                directFlightsObserver()
            }
        }
    }

    private suspend fun musicFlightsObserver() {
        viewModel.musicFlights.collectLatest {
            musicFlightAdapter?.swapData(it)
        }
    }

    private suspend fun toastObserver() {
        viewModel.errorToast.collectLatest { errorType ->
            val errorMessage = when (errorType) {
                MainFragmentViewModel.Error.DOWNLOADING_ERROR -> getString(R.string.downloading_error)
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private suspend fun editTextsObserver() {
        viewModel.editTextsDataState.collectLatest {
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

    private suspend fun dateOfFlightObserver() {
        viewModel.dateOfFlightState.collectLatest {
            val spannableString = getSpannableStringOfDate(it)
            bd.btnSelectDateDestinationSelected.text = spannableString
        }
    }

    private suspend fun directFlightsObserver() {
        viewModel.directFlights.collectLatest {
            directFlightAdapter?.swapData(it)
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
                editTextsDataState.value = editTextsDataState.value.copy(
                    departurePlaceText = editTextsDataState.value.destinationPlaceText,
                    destinationPlaceText = editTextsDataState.value.departurePlaceText
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
                    Toast.makeText(context, "Выбран обратный билет. Дата - ${dayOfMonth}.${month}.${year}", Toast.LENGTH_LONG).show()
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
                    viewModel.dateOfFlightState.value = LocalDate.of(year, month+1, dayOfMonth) // month is starting from 0 in datePickerDialog
                }
                datePickerDialog.show()
            }
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
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_6)),
                indexOfComma,
                spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        return spannableString
    }

    private fun saveEditTextsToState() {
        viewModel.editTextsDataState.value = EditTextsData(
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}