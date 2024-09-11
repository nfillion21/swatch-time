package xyz.poolp.core.common.date

import kotlinx.datetime.Clock
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.TimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toLocalDateTime

object TimeExtensions {

    // in a 24h day there is 86400 seconds or 1000 beats, then 1 beat = 86.4 seconds
    private const val ONE_BEAT_TO_SECONDS: Float = 86.4f

    fun currentSwatchTime(): String = run {
        // swatch time is fixed UTC+01:00
        val timeZone = FixedOffsetTimeZone(UtcOffset(hours = 1))
        now(timeZone).time.toSecondOfDay().div(ONE_BEAT_TO_SECONDS).round()
    }

    private fun now(timeZone: TimeZone = TimeZone.currentSystemDefault()) =
        Clock.System.now().toLocalDateTime(timeZone)

    private fun Float.round(decimals: Int = 2): String = "%.${decimals}f".format(this)
}