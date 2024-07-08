package com.drexask.effectivemobiletickets.presentation.ui.fragment.aviaTicketsFragment

import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.viewModelScope
import com.drexask.effectivemobiletickets.R
import com.drexask.musicflights.domain.model.MusicFlightDto
import com.drexask.musicflights.domain.usecase.GetMusicFlightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AviaTicketsFragmentViewModel @Inject constructor(
    private val getMusicFlightsUseCase: GetMusicFlightsUseCase
): ViewModel() {

    private val _musicFlights: MutableStateFlow<List<MusicFlightDto>> = MutableStateFlow(emptyList())
    val musicFlights = _musicFlights.asStateFlow()

    init {
        viewModelScope.launch {
            val result = getMusicFlightsUseCase.execute()

            if(result.isSuccess) {
                _musicFlights.value = result.getOrNull()!!
            } else {
                result.exceptionOrNull()?.printStackTrace()

            }
        }
    }

}