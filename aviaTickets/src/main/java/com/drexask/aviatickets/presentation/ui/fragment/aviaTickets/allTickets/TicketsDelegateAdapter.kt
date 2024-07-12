package com.drexask.presentation.ui.fragment.aviaTickets.allTickets

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.drexask.presentation.R
import com.drexask.presentation.databinding.CardTicketBinding
import com.drexask.presentation.models.TicketUi
import com.drexask.presentation.utils.applyPriceDecorator
import com.drexask.presentation.utils.convertDpToPx
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import java.time.Duration
import java.time.format.DateTimeFormatter


class TicketsDelegateAdapter :
    ViewBindingDelegateAdapter<TicketUi,
            CardTicketBinding>(CardTicketBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is TicketUi

    @SuppressLint("SetTextI18n")
    override fun CardTicketBinding.onBind(item: TicketUi) {
        if (item.badge != null) {
            cardBadge.visibility = View.VISIBLE
            tvBadge.text = item.badge

            setupTicketCardMargin(root.context, cardTicket, true)
            setupTicketPriceMargin(root.context, tvTicketPrice, true)
        } else {
            cardBadge.visibility = View.GONE
            tvBadge.text = ""

            setupTicketCardMargin(root.context, cardTicket, false)
            setupTicketPriceMargin(root.context, tvTicketPrice, false)
        }

        tvTicketPrice.text = root.context.resources.getString(
            R.string.price_text,
            item.priceUi.value.toString().applyPriceDecorator()
        )

        val hoursMinutesFormat = DateTimeFormatter.ofPattern("HH:mm")

        tvTimeDeparture.text = item.departureUi.localDateTime.format(hoursMinutesFormat)
        tvAirportDeparture.text = item.departureUi.airport

        tvTimeDestination.text = item.arrivalUi.localDateTime.format(hoursMinutesFormat)
        tvAirportDestination.text = item.arrivalUi.airport

        tvTravelTimeAndInfo.text = getSpannableTravelTimeAndInfo(item, root.context)

    }

    private fun getSpannableTravelTimeAndInfo(
        item: TicketUi,
        context: Context
    ): SpannableString {
        val flightDuration = Duration.between(item.departureUi.localDateTime, item.arrivalUi.localDateTime)

        val roundedFlightDuration = getRoundedFlightDuration(flightDuration)

        val flightDurationString =
            context.getString(R.string.flight_duration, roundedFlightDuration)
        val transferString = if (item.hasTransfer) "" else context.getString(R.string.without_transfer)
        val resultString = flightDurationString + transferString

        val spannableString = SpannableString(resultString)

        if (!item.hasTransfer) { // We need to color the slash gray if there is no transfer
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

    private fun setupTicketCardMargin(context: Context, card: CardView, badge: Boolean) {
        val topMargin = if (badge) 7 else 0

        val topMarginPx = convertDpToPx(context, topMargin)

        val newLayoutParams = FrameLayout.LayoutParams(card.layoutParams).apply {
            setMargins(0, topMarginPx, 0, 0)
        }
        card.layoutParams = newLayoutParams
    }

    private fun setupTicketPriceMargin(context: Context, priceView: TextView, badge: Boolean) {
        val leftMargin = 20
        val topMargin = if (badge) 20 else 12

        val leftMarginPx = convertDpToPx(context, leftMargin)
        val topMarginPx = convertDpToPx(context, topMargin)

        val newLayoutParams = (priceView.layoutParams as ConstraintLayout.LayoutParams).apply {
            setMargins(leftMarginPx, topMarginPx, 0, 0)
        }

        priceView.layoutParams = newLayoutParams
    }

    private fun getRoundedFlightDuration(flightDuration: Duration): String {
        var flightHours = flightDuration.toHours()
        var flightPartOfHour =
            (((flightDuration.toMinutes() % 60) / 60.0) * 10).toInt() // e.g. 0 will be 0, 30 will be 5, 59 will be 9

        flightPartOfHour = when(flightPartOfHour) {
            in 0..3 -> 0
            in 4..6 -> 5
            else -> { // round to bigger, e.g. 3.7 will be 4.0
                flightHours++
                0
            }
        }

        val resultDuration = flightHours.toString() +
                (if (flightPartOfHour != 0) ".$flightPartOfHour" else "")
        return resultDuration
    }

    override fun TicketUi.getItemId(): Any = id
}
