package com.drexask.presentation.ui.fragment.aviaTickets.allTickets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drexask.domain.model.Ticket
import com.drexask.domain.repository.TicketsRepository
import com.drexask.domain.usecase.GetTicketsUseCase
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

    private val _tickets: MutableStateFlow<List<Ticket>> = MutableStateFlow(emptyList())
    internal val tickets = _tickets.asStateFlow()

    private val _errorToasts = MutableSharedFlow<ToastErrorType>()
    internal val errorToast = _errorToasts.asSharedFlow()

    init {
        viewModelScope.launch {
            val result = GetTicketsUseCase(ticketsRepository).execute()

            if(result.isSuccess) {
                _tickets.value = result.getOrNull()!!
            } else {
                result.exceptionOrNull()?.printStackTrace()
                _errorToasts.emit(ToastErrorType.DOWNLOADING_ERROR)
            }
        }
    }


}