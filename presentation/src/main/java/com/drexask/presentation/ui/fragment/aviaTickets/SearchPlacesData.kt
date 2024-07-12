package com.drexask.presentation.ui.fragment.aviaTickets

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
internal data class SearchPlacesData(
    val departurePlaceText: String?,
    val destinationPlaceText: String?
) : Parcelable
