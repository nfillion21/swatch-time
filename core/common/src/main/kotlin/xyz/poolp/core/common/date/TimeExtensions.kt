package xyz.poolp.core.common.date

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toLocalDateTime

object TimeExtensions {

    // in a 24h day there is 86400 seconds or 1000 beats, then 1 beat = 86.4 seconds
    private const val BEAT_TO_SECONDS: Float = 86.4f

    // swatch time is fixed UTC+01:00
    private val swatchZone: FixedOffsetTimeZone = FixedOffsetTimeZone(UtcOffset(hours = 1))

    fun swatchTime(localDateTime: LocalDateTime): String = run {
        localDateTime.time.toSecondOfDay().div(BEAT_TO_SECONDS).round()
    }

    fun swatchDate(localDateTime: LocalDateTime): String = with(localDateTime) {
        val f: NumberFormat = DecimalFormat("00")
        "d${f.format(date.dayOfMonth)}.${f.format(date.monthNumber)}.${year}"
    }

    fun swatchDateNow() = Clock.System.now().toLocalDateTime(swatchZone)

    private fun Float.round(decimals: Int = 2): String = "%.${decimals}f".format(this)
}