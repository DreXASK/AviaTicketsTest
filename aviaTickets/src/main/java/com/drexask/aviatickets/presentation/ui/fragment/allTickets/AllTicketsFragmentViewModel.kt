package com.drexask.aviatickets.presentation.ui.fragment.allTickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexask.aviatickets.domain.repository.TicketsRepository
import com.drexask.aviatickets.domain.usecase.GetTicketsUseCase
import com.drexask.aviatickets.presentation.models.TicketUi
import com.drexask.aviatickets.presentation.utils.ToastErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllTicketsFragmentViewModel @Inject constructor(
    private val getTicketsUseCase: GetTicketsUseCase
): ViewModel() {

    private val _tickets: MutableStateFlow<List<TicketUi>> = MutableStateFlow(emptyList())
    internal val tickets = _tickets.asStateFlow()

    private val _errorToasts = MutableSharedFlow<ToastErrorType>()
    internal val errorToast = _errorToasts.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = getTicketsUseCase.execute()

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