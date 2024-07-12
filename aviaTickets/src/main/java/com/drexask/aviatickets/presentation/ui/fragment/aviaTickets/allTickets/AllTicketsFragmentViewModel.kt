package com.drexask.presentation.ui.fragment.aviaTickets.allTickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexask.domain.models.Ticket
import com.drexask.domain.repository.TicketsRepository
import com.drexask.domain.usecase.GetTicketsUseCase
import com.drexask.presentation.models.TicketUi
import com.drexask.presentation.utils.ToastErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTicketsFragmentViewModel @Inject constructor(
    private val ticketsRepository: TicketsRepository
): ViewModel() {

    private val _tickets: MutableStateFlow<List<TicketUi>> = MutableStateFlow(emptyList())
    internal val tickets = _tickets.asStateFlow()

    private val _errorToasts = MutableSharedFlow<ToastErrorType>()
    internal val errorToast = _errorToasts.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = GetTicketsUseCase(ticketsRepository).execute()

            if(result.isSuccess) {
                val tickets = result.getOrThrow().map {
                    TicketUi.mapFromDomainModel(it)
                }

                _tickets.value = tickets
            } else {
                result.exceptionOrNull()?.printStackTrace()
                _errorToasts.emit(ToastErrorType.DOWNLOADING_ERROR)
            }
        }
    }


}