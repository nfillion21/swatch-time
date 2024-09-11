package xyz.poolp.feature.time

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.koin.androidx.compose.koinViewModel
import xyz.poolp.core.common.date.TimeExtensions

@Composable
fun TimeScreen(
    timeViewModel: TimeViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) { innerPadding ->
        val tickInterval by timeViewModel.tickFlow.collectAsState(TimeExtensions.swatchDateNow())
        TimeContent(
            currentTime = tickInterval,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        )
    }
}

@Composable
private fun TimeContent(
    currentTime: LocalDateTime,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            text = "Swatch Time",
            style = MaterialTheme.typography.bodyLarge
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = TimeExtensions.swatchDate(currentTime),
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "@${TimeExtensions.swatchTime(currentTime)}",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
