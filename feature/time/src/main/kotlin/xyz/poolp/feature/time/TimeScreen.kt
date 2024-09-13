package xyz.poolp.feature.time

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import org.koin.androidx.compose.koinViewModel

@Composable
fun TimeScreen(
    onSharePressed: (String) -> Unit,
    timeViewModel: TimeViewModel = koinViewModel()
) {
    val timeState by timeViewModel.timeUiState.collectAsState(TimeViewModel.TimeUiState())
    TimeContent(
        timeState = timeState,
        onSharePressed = { onSharePressed(timeViewModel.sharedTime()) }
    )
}

@Composable
private fun TimeContent(
    timeState: TimeViewModel.TimeUiState,
    onSharePressed: () -> Unit,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                //onClick = { onSharePressed(timeViewModel.sharedTime()) },
                onClick = onSharePressed,
                icon = { Icon(Icons.Filled.Share, "Share floating action button.") },
                text = { Text(text = "Share time") },
            )
        }
    ) { innerPadding ->
        val pagerState = rememberPagerState(initialPage = 0) { 2 }
        val scope = rememberCoroutineScope()

        HorizontalPager(
            state = pagerState,
            pageSpacing = 10.dp
        ) { currentPage ->

            val timeType = if (currentPage == 0) TimeType.SWATCH else TimeType.LOCAL
            TimePage(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                onSwitchPressed = { to ->
                    val page = when (to) {
                        SwitchOrientation.RIGHT -> 1
                        SwitchOrientation.LEFT -> 0
                    }
                    scope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                },
                timeType = timeType,
                timeState = timeState,
            )
        }
    }
}

@Composable
private fun TimePage(
    onSwitchPressed: (SwitchOrientation) -> Unit,
    timeType: TimeType,
    timeState: TimeViewModel.TimeUiState,
    modifier: Modifier
) {
    Column(
        modifier = modifier.padding(top = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = if (timeType == TimeType.SWATCH) "Swatch Time" else "Local Time",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = if (timeType == TimeType.SWATCH) "Internet Time" else TimeZone.currentSystemDefault()
                    .toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1.0f)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = if (timeType == TimeType.SWATCH) timeState.swatchDate else timeState.localDate,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = if (timeType == TimeType.SWATCH) timeState.swatchTime else timeState.localTime,
                style = MaterialTheme.typography.headlineLarge
            )

            IconButton(onClick = { onSwitchPressed(switchOrientation(timeType)) }) {
                Icon(
                    painter = painterResource(if (timeType == TimeType.SWATCH) R.drawable.switch_right_24px else R.drawable.switch_left_24px),
                    contentDescription = null
                )
            }
        }

        Spacer(
            modifier = Modifier
                .weight(1.0f)
        )
    }
}

private fun switchOrientation(from: TimeType) =
    when (from) {
        TimeType.SWATCH -> SwitchOrientation.RIGHT
        TimeType.LOCAL -> SwitchOrientation.LEFT
    }

private enum class TimeType {
    SWATCH, LOCAL
}

enum class SwitchOrientation {
    RIGHT, LEFT
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TimeContentPreview() {
    MaterialTheme {
        TimeContent(
            timeState = TimeViewModel.TimeUiState(
                swatchDate = "d12.09.2024",
                swatchTime = "@441.13"
            ),
            onSharePressed = {},
        )
    }
}
