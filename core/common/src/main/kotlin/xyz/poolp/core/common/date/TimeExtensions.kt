package xyz.poolp.core.common.date

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toLocalDateTime

object TimeExtensions {

    // in a 24h day there is 86400 seconds or 1000 beats, then 1 beat = 86.4 seconds
    private const val ONE_BEAT_TO_SECONDS: Float = 86.4f

    // swatch time is fixed UTC+01:00
    private val swatchZone: FixedOffsetTimeZone
        get() = FixedOffsetTimeZone(UtcOffset(hours = 1))

    fun currentSwatchTime(): String = run {
        now(swatchZone).time.toSecondOfDay().div(ONE_BEAT_TO_SECONDS).round()
    }

    fun currentSwatchDate(): String = with(now(swatchZone)) {
        val f: NumberFormat = DecimalFormat("00")
        "d${f.format(date.dayOfMonth)}.${f.format(date.monthNumber)}.${year}"
    }

    private fun now(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
        Clock.System.now().toLocalDateTime(timeZone)

    private fun Float.round(decimals: Int = 2): String = "%.${decimals}f".format(this)
}