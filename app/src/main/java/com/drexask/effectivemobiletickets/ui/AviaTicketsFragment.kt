package com.drexask.effectivemobiletickets.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.drexask.effectivemobiletickets.SpaceItemDecoration
import com.drexask.effectivemobiletickets.databinding.FragmentAviaTicketsBinding
import com.drexask.musicflights.domain.usecase.GetMusicFlightsUseCase
import com.drexask.musicflights.presentation.MusicFlightsDelegateAdapter
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AviaTicketsFragment: Fragment() {

    private var _binding: FragmentAviaTicketsBinding? = null
    private val bd: FragmentAviaTicketsBinding
        get() = _binding!!

    @Inject
    lateinit var getMusicFlightsUseCase: GetMusicFlightsUseCase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAviaTicketsBinding.inflate(layoutInflater)

        val adapter = CompositeDelegateAdapter(MusicFlightsDelegateAdapter())

        lifecycle.coroutineScope.launch {

            val result = try {
                val result = getMusicFlightsUseCase.execute()
                result
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }


            if(result.isSuccess) {
                val list = result.getOrNull()!!
                adapter.swapData(list)
            } else {
                result.exceptionOrNull()?.printStackTrace()
            }
        }

//        adapter.swapData(
//            listOf(
//                MusicFlightDto(
//                    id = 1,
//                    "СЛОТ",
//                    "Костанай",
//                    Price(1000)
//                ),
//                MusicFlightDto(
//                    id = 2,
//                    "Louna",
//                    "Челябинск",
//                    Price(2000)
//                ),
//                MusicFlightDto(
//                    id = 3,
//                    "Three Days Grace",
//                    "Москва",
//                    Price(5000)
//                )
//            )
//        )

        bd.musicFlightsRecycler.adapter = adapter
        bd.musicFlightsRecycler.layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)

        bd.musicFlightsRecycler.addItemDecoration(SpaceItemDecoration(100))

        return bd.root
    }
}

class testClass3 @Inject constructor() {

    fun printtt() = println("889989899988989")
}