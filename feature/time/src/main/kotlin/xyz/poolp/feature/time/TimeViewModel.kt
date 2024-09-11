package xyz.poolp.feature.time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import xyz.poolp.core.common.date.TimeExtensions
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class TimeViewModel : ViewModel() {
    private val _tickFlow = MutableStateFlow(TimeExtensions.swatchDateNow())
    val tickFlow: StateFlow<LocalDateTime> = _tickFlow.asStateFlow()

    // 1 beat = 1.26 s = 1260 ms
    private val tickInterval: Duration = 1260.milliseconds

    init {
        viewModelScope.launch {
            while (true) {
                _tickFlow.emit(TimeExtensions.swatchDateNow())
                delay(tickInterval)
            }
        }
    }
}
