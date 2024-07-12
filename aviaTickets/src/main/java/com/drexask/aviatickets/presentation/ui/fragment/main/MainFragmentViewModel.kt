package com.drexask.aviatickets.presentation.ui.fragment.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexask.aviatickets.domain.repository.DirectFlightsRepository
import com.drexask.aviatickets.domain.repository.MusicFlightsRepository
import com.drexask.aviatickets.domain.usecase.GetDirectFlightsUseCase
import com.drexask.aviatickets.domain.usecase.GetMusicFlightsUseCase
import com.drexask.aviatickets.presentation.models.DirectFlightUi
import com.drexask.aviatickets.presentation.models.MusicFlightUi
import com.drexask.aviatickets.presentation.models.bundleModels.SearchPlacesData
import com.drexask.aviatickets.presentation.utils.ToastErrorType
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
    private val musicFlightsRepository: MusicFlightsRepository,
    private val directFlightRepository: DirectFlightsRepository
): ViewModel() {

    internal val screenState = MutableStateFlow(ScreenState.DEFAULT)
    internal val searchPlacesData = MutableStateFlow(SearchPlacesData(null, null))
    internal val searchDateOfFlightState = MutableStateFlow(LocalDate.now())

    private val _musicFlights: MutableStateFlow<List<MusicFlightUi>> = MutableStateFlow(emptyList())
    internal val musicFlights = _musicFlights.asStateFlow()

    private val _directFlights: MutableStateFlow<List<DirectFlightUi>> = MutableStateFlow(emptyList())
    internal val directFlights = _directFlights.asStateFlow()

    private val _errorToasts = MutableSharedFlow<ToastErrorType>()
    internal val errorToast = _errorToasts.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = GetMusicFlightsUseCase(musicFlightsRepository).execute()

            if(result.isSuccess) {
                val musicFlights = result.getOrThrow().map {
                    MusicFlightUi.mapFromDomainModel(it)
                }

                _musicFlights.value = musicFlights
            } else {
                result.exceptionOrNull()?.printStackTrace()
                _errorToasts.emit(ToastErrorType.DOWNLOADING_ERROR)
            }
        }
        viewModelScope.launch {
            val result = GetDirectFlightsUseCase(directFlightRepository).execute()

            if(result.isSuccess) {
                val directFlights = result.getOrThrow().map {
                    DirectFlightUi.mapFromDomainModel(it)
                }
                _directFlights.value = directFlights
            } else {
                result.exceptionOrNull()?.printStackTrace()
                _errorToasts.emit(ToastErrorType.DOWNLOADING_ERROR)
            }
        }
    }

    internal fun clearDestination() {
        searchPlacesData.value = searchPlacesData.value.copy(
            destinationPlaceText = ""
        )
        screenState.value = ScreenState.DEFAULT
    }

    internal enum class ScreenState {
        DEFAULT,
        DESTINATION_SELECTED
    }

}