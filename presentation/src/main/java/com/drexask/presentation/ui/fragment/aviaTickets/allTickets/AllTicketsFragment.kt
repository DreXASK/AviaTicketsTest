package com.drexask.presentation.ui.fragment.aviaTickets.allTickets

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.presentation.R
import com.drexask.presentation.databinding.FragmentAviaTicketsAllTicketsBinding
import com.drexask.presentation.ui.fragment.aviaTickets.main.DirectFlightDelegateAdapter
import com.drexask.presentation.ui.fragment.aviaTickets.main.MainFragmentViewModel
import com.drexask.presentation.utils.ToastErrorType
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

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

        setupListeners()
        setupObservers()
        setupRecycler()

        return bd.root
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
    }

    private fun clickGoBack() {
        bd.ivGoBackToMain.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecycler() {
        ticketsAdapter = CompositeDelegateAdapter(TicketsDelegateAdapter())

        bd.rvTickets.adapter = ticketsAdapter
        bd.rvTickets.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
    }

}