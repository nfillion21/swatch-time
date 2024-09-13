package xyz.poolp.feature.time

import android.content.res.Configuration
import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun TimeScreen(
    onSharePressed: (String) -> Unit,
    timeViewModel: TimeViewModel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { onSharePressed(timeViewModel.sharedTime()) },
                icon = { Icon(Icons.Filled.Share, "Share floating action button.") },
                text = { Text(text = "Share time") },
            )
        }
    ) { innerPadding ->
        val timeState by timeViewModel.timeUiState.collectAsState(TimeViewModel.TimeUiState())
        val pagerState = rememberPagerState(initialPage = 0) { 2 }
        val scope = rememberCoroutineScope()

        HorizontalPager(
            modifier = Modifier.padding(innerPadding),
            state = pagerState,
            pageSpacing = 10.dp
        ) { currentPage ->
            TimeContent(
                timeType = if (currentPage == 0) TimeType.SWATCH else TimeType.LOCAL,
                timeState = timeState,
                onSwitchPressed = { to ->
                    val page = when (to) {
                        SwitchOrientation.RIGHT -> 1
                        SwitchOrientation.LEFT -> 0
                    }
                    scope.launch {
                        pagerState.animateScrollToPage(page)
                    }
                },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}

@Composable
private fun TimeContent(
    timeType: TimeType,
    timeState: TimeViewModel.TimeUiState,
    onSwitchPressed: (SwitchOrientation) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 64.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            Text(
                text = if (timeType == TimeType.SWATCH) "Swatch Time" else "Local Time",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "by poolp",
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier
                .align(Alignment.Center),
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

            IconButton(onClick = { onSwitchPressed.invoke(switchOrientation(timeType)) }) {
                Icon(
                    painter = painterResource(if (timeType == TimeType.SWATCH) R.drawable.switch_right_24px else R.drawable.switch_left_24px),
                    contentDescription = null
                )
            }
        }
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

private enum class SwitchOrientation {
    RIGHT, LEFT
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TimeContentPreview() {
    MaterialTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { },
                    icon = { Icon(Icons.Filled.Share, "Share floating action button.") },
                    text = { Text(text = "Share time") },
                )
            }
        ) { innerPadding ->
            TimeContent(
                timeType = TimeType.SWATCH,
                timeState = TimeViewModel.TimeUiState(
                    swatchDate = "d12.09.2024",
                    swatchTime = "@441.13"
                ),
                onSwitchPressed = {},
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            )
        }
    }
}
