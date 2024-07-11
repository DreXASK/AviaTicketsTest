package com.drexask.presentation.ui.fragment.aviaTickets.main

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.LayoutRes
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.domain.model.PopularDestination
import com.drexask.presentation.R
import com.drexask.presentation.databinding.FragmentAviaTicketsDestinationBottomSheetBinding
import com.drexask.presentation.utils.DEPARTURE_CACHE
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DestinationBottomSheetFragment(@LayoutRes layoutRes: Int) :
    BottomSheetDialogFragment(layoutRes) {

    private var _binding: FragmentAviaTicketsDestinationBottomSheetBinding? = null
    private val bd get() = _binding!!

    private val viewModel: MainFragmentViewModel by activityViewModels()
    private var adapter: CompositeDelegateAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        _binding = view?.let { FragmentAviaTicketsDestinationBottomSheetBinding.bind(it) }

        setupRecyclerView()
        setupListeners()
        setupObservers()

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val dialog = super.onCreateDialog(savedInstanceState)

        // Setup fullscreen mode
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(
                com.google.android.material.R.id.design_bottom_sheet
            )
            parentLayout?.let { bottomSheet ->
                val behaviour = BottomSheetBehavior.from(bottomSheet)

                val layoutParams = bottomSheet.layoutParams
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT

                bottomSheet.layoutParams = layoutParams
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }

        return dialog
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        tryChangeMainFragmentStateToSelected()
        saveEditTextsToState()
        cacheDepartureFieldValue()
    }

    private fun saveEditTextsToState() {
        viewModel.editTextsDataState.value = EditTextsData(
            departurePlaceText = bd.etDeparture.text.toString(),
            destinationPlaceText = bd.etDestination.text.toString()
        )
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

    private fun tryChangeMainFragmentStateToSelected() {
        if(bd.etDeparture.text.isNotBlank() && bd.etDestination.text.isNotBlank()) {
            viewModel.screenState.value = MainFragmentViewModel.ScreenState.DESTINATION_SELECTED
        }
    }

    private fun setupRecyclerView() {
        adapter = CompositeDelegateAdapter(
            PopularDestinationsDelegateAdapter {
                bd.etDestination.setText(it)
            }
        )

        bd.rvPopularDestinations.adapter = adapter
        bd.rvPopularDestinations.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        val popularDestinationData = listOf(
            PopularDestination(
                1,
                "Стамбул",
                "Популярное направление"
            ),
            PopularDestination(
                2,
                "Сочи",
                "Популярное направление"
            ),
            PopularDestination(
                3,
                "Пхукет",
                "Популярное направление"
            )
        )

        adapter?.swapData(popularDestinationData)
    }

    private fun setupObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                editTextsObserver()
            }
        }
    }

    private suspend fun editTextsObserver() {
        viewModel.editTextsDataState.collectLatest {
            it.departurePlaceText?.let { departureText ->
                bd.etDeparture.setText(departureText)
            }
            it.destinationPlaceText?.let { destinationText ->
                bd.etDestination.setText(destinationText)
            }
        }
    }

    private fun setupListeners() {
        clickClearDestination()
        clickComplexRoute()
        clickToAnywhere()
        clickWeekend()
        clickHotTickets()
    }

    private fun clickClearDestination() {
        bd.ivClearTextField.setOnClickListener {
            bd.etDestination.text.clear()
        }
    }

    private fun clickComplexRoute() {
        bd.btnComplexRoute.setOnClickListener {
            findNavController().navigate(R.id.complexRouteFragment)
        }
    }

    private fun clickToAnywhere() {
        bd.btnToAnywhere.setOnClickListener {
            bd.etDestination.setText(
                listOf("Стамбул", "Пхукет", "Сочи").random()
            )
        }
    }

    private fun clickWeekend() {
        bd.btnWeekend.setOnClickListener {
            findNavController().navigate(R.id.weekendFragment)
        }
    }

    private fun clickHotTickets() {
        bd.btnHotTickets.setOnClickListener {
            findNavController().navigate(R.id.hotTicketsFragment)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

