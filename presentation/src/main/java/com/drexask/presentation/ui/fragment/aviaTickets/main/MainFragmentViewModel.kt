package com.drexask.presentation.ui.fragment.aviaTickets.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexask.domain.model.MusicFlight
import com.drexask.domain.repository.MusicFlightsRepository
import com.drexask.domain.usecase.GetMusicFlightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val musicFlightsRepository: MusicFlightsRepository
): ViewModel() {

    internal val screenState = MutableStateFlow(ScreenState.DEFAULT)
    internal val editTextsDataState = MutableStateFlow(EditTextsData(null, null))
    internal val dateOfFlightState = MutableStateFlow(LocalDate.now())

    private val _musicFlights: MutableStateFlow<List<MusicFlight>> = MutableStateFlow(emptyList())
    internal val musicFlights = _musicFlights.asStateFlow()

    private val _errorToasts = MutableSharedFlow<Error>()
    internal val errorToast = _errorToasts.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = GetMusicFlightsUseCase(musicFlightsRepository).execute()

            if(result.isSuccess) {
                _musicFlights.value = result.getOrNull()!!
            } else {
                result.exceptionOrNull()?.printStackTrace()
                _errorToasts.emit(Error.DOWNLOADING_ERROR)
            }
        }
    }

    internal fun clearDestination() {
        editTextsDataState.value = editTextsDataState.value.copy(
            destinationPlaceText = ""
        )
        screenState.value = ScreenState.DEFAULT
    }

    internal enum class Error {
        DOWNLOADING_ERROR
    }

    internal enum class ScreenState {
        DEFAULT,
        DESTINATION_SELECTED
    }

}

internal data class EditTextsData(
    val departurePlaceText: String?,
    val destinationPlaceText: String?
)