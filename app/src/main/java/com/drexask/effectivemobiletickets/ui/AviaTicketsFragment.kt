package com.drexask.effectivemobiletickets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.effectivemobiletickets.SpaceItemDecoration
import com.drexask.effectivemobiletickets.databinding.FragmentAviaTicketsBinding
import com.drexask.musicflights.domain.model.MusicFlightDto
import com.drexask.musicflights.domain.model.Price
import com.drexask.musicflights.presentation.MusicFlightsDelegateAdapter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter


class AviaTicketsFragment : Fragment() {

    private var _binding: FragmentAviaTicketsBinding? = null
    private val bd: FragmentAviaTicketsBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAviaTicketsBinding.inflate(layoutInflater)

        val adapter = CompositeDelegateAdapter(MusicFlightsDelegateAdapter())

        adapter.swapData(
            listOf(
                MusicFlightDto(
                    id = 1,
                    "СЛОТ",
                    "Костанай",
                    Price(1000)
                ),
                MusicFlightDto(
                    id = 2,
                    "Louna",
                    "Челябинск",
                    Price(2000)
                ),
                MusicFlightDto(
                    id = 3,
                    "Three Days Grace",
                    "Москва",
                    Price(5000)
                )
            )
        )

        bd.musicFlightsRecycler.adapter = adapter
        bd.musicFlightsRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)


        bd.musicFlightsRecycler.addItemDecoration(SpaceItemDecoration(100))


        return bd.root
    }
}