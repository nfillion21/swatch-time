package xyz.poolp.core.common.date

import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import kotlinx.datetime.Clock
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toLocalDateTime

object TimeExtensions {

    // in a 24h day there is 86400 seconds or 1000 beats,
    // then 1 beat = 86.4 seconds or 86400 milliseconds
    private const val BEAT_TO_MS: Float = 86400f

    // swatch time is fixed UTC+01:00
    private val swatchZone: FixedOffsetTimeZone = FixedOffsetTimeZone(UtcOffset(hours = 1))

    fun swatchTime(): String = run {
        swatchDateNow().time.toMillisecondOfDay().div(BEAT_TO_MS).round()
    }

    fun swatchDate(): String = with(swatchDateNow()) {
        val f: NumberFormat = DecimalFormat("00")
        "d${f.format(date.dayOfMonth)}.${f.format(date.monthNumber)}.${year}"
    }

    private fun swatchDateNow() = Clock.System.now().toLocalDateTime(swatchZone)

    private fun Float.round(digits:Int = 6, decimals: Int = 2): String = "%0${digits}.${decimals}f".format(this)
}