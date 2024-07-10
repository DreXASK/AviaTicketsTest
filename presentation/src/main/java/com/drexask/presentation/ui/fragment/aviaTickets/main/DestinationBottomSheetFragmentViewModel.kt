package com.drexask.presentation.ui.fragment.aviaTickets.main

import androidx.lifecycle.ViewModel
import com.drexask.domain.model.PopularDestination
import javax.inject.Inject

class DestinationBottomSheetFragmentViewModel @Inject constructor(): ViewModel() {

    val popularDestinationData = listOf(
        PopularDestination(
            1,
            "Стамбул",
            "Популярное направление"
        ),
        PopularDestination(
            2,
            "Сочи",
            "Популярное направление"
        ),
        PopularDestination(
            3,
            "Пхукет",
            "Популярное направление"
        ),
    )

}
