package com.drexask.presentation.ui.fragment.aviaTickets.allTickets

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.format.DateFormat
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.FrameLayout
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.drexask.domain.model.Ticket
import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardAllTicketsFlightBinding
import com.drexask.presentation.utils.applyPriceDecorator
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import java.time.Duration
import java.time.format.DateTimeFormatter

class TicketsDelegateAdapter :
    ViewBindingDelegateAdapter<Ticket,
            CardAllTicketsFlightBinding>(CardAllTicketsFlightBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is Ticket

    override fun CardAllTicketsFlightBinding.onBind(item: Ticket) {
        if (item.badge != null) {
            cardBadge.visibility = View.VISIBLE
            tvBadge.text = item.badge

            setupTicketCardMargin(cardTicket, true)
        } else {
            cardBadge.visibility = View.GONE
            tvBadge.text = ""

            setupTicketCardMargin(cardTicket, false)
        }

        tvTicketPrice.text = root.context.resources.getString(
            R.string.price_text,
            item.price.value.toString().applyPriceDecorator()
        )

        val hoursMinutesFormat = DateTimeFormatter.ofPattern("HH:mm")

        tvTimeDeparture.text = item.departure.dateTime.format(hoursMinutesFormat)
        tvAirportDeparture.text = item.departure.airport

        tvTimeDestination.text = item.arrival.dateTime.format(hoursMinutesFormat)
        tvAirportDestination.text = item.arrival.airport

        tvTravelTimeAndInfo.text = getSpannableTravelTimeAndInfo(item, root.context)

    }

    private fun getSpannableTravelTimeAndInfo(
        item: Ticket,
        context: Context
    ): SpannableString {
        val flightDuration = Duration.between(item.departure.dateTime, item.arrival.dateTime)


        val flightHours = flightDuration.toHours()
        val flightPartOfHour =
            (((flightDuration.toMinutes() % 60) / 60.0) * 10).toInt() // e.g. 0 will be 0, 30 will be 5, 59 will be 9

        val flightDurationString = "$flightHours.${flightPartOfHour}ч в пути"
        val transferString = if (item.hasTransfer) "" else " / Без пересадок"
        val resultString = flightDurationString + transferString

        val spannableString = SpannableString(resultString)

        if (!item.hasTransfer) { // We need to color slash if there is no transfer
            val indexOfSlash = resultString.indexOf("/")

            spannableString.setSpan(
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.grey_6)),
                indexOfSlash,
                indexOfSlash + 1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        return spannableString
    }

    private fun setupTicketCardMargin(card: CardView, badge: Boolean) {
        val topMargin = if (badge) 7 else 0

        val layoutParams = FrameLayout.LayoutParams(card.layoutParams)
        layoutParams.setMargins(0, topMargin, 0, 0)
        card.layoutParams = layoutParams
    }

    override fun Ticket.getItemId(): Any = id
}
