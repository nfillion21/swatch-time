package xyz.poolp.feature.time

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import xyz.poolp.core.common.date.TimeExtensions
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class TimeViewModel : ViewModel() {
    private val _timeUiState = MutableStateFlow(TimeUiState())
    val timeUiState: StateFlow<TimeUiState> = _timeUiState.asStateFlow()

    private val tickInterval: Duration = 512.milliseconds

    init {
        viewModelScope.launch {
            while (true) {
                _timeUiState.update {
                    it.copy(
                        swatchDate = TimeExtensions.swatchDate(),
                        swatchTime = "@${TimeExtensions.swatchTime()}"
                    )
                }
                delay(tickInterval)
            }
        }
    }

    data class TimeUiState(
        val swatchDate: String = "",
        val swatchTime: String = ""
    )
}
