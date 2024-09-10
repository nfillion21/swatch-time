package xyz.poolp.core.common.date

import kotlinx.datetime.Clock
import kotlinx.datetime.FixedOffsetTimeZone
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

object DateExtensions {
    fun currentSwatchTime(): Int = run {
        val timeZone = FixedOffsetTimeZone(UtcOffset(hours = 1))
        val now = Clock.System.now().toLocalDateTime(timeZone)
        ((now.hour * 3600 + now.minute * 60 + now.second) / 86.4).roundToInt()
    }
}

