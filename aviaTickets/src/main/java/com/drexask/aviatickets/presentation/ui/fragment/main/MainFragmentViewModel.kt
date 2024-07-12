package com.drexask.aviatickets.presentation.ui.fragment.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexask.aviatickets.domain.usecase.GetTicketOffersUseCase
import com.drexask.aviatickets.domain.usecase.GetMusicFlightsUseCase
import com.drexask.aviatickets.presentation.models.TicketOfferUi
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
    private val getMusicFlightsUseCase: GetMusicFlightsUseCase,
    private val getTicketOffersUseCase: GetTicketOffersUseCase
): ViewModel() {

    internal val screenState = MutableStateFlow(ScreenState.DEFAULT)
    internal val searchPlacesData = MutableStateFlow(SearchPlacesData(null, null))
    internal val searchDateOfFlightState = MutableStateFlow(LocalDate.now())

    private val _musicFlights: MutableStateFlow<List<MusicFlightUi>> = MutableStateFlow(emptyList())
    internal val musicFlights = _musicFlights.asStateFlow()

    private val _ticketOffers: MutableStateFlow<List<TicketOfferUi>> = MutableStateFlow(emptyList())
    internal val ticketOffers = _ticketOffers.asStateFlow()

    private val _errorToasts = MutableSharedFlow<ToastErrorType>()
    internal val errorToast = _errorToasts.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = getMusicFlightsUseCase.execute()

            if(result.isSuccess) {
                val musicFlightsReceived = result.getOrThrow().map {
                    MusicFlightUi.mapFromDomainModel(it)
                }

                _musicFlights.value = musicFlightsReceived
            } else {
                result.exceptionOrNull()?.printStackTrace()
                _errorToasts.emit(ToastErrorType.DOWNLOADING_ERROR)
            }
        }
        viewModelScope.launch {
            val result = getTicketOffersUseCase.execute()

            if(result.isSuccess) {
                val ticketOffersReceived = result.getOrThrow().take(3).map {
                    TicketOfferUi.mapFromDomainModel(it)
                }
                _ticketOffers.value = ticketOffersReceived
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