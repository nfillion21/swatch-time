package xyz.poolp.feature.time

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import xyz.poolp.feature.time.composable.LocalTimePage
import xyz.poolp.feature.time.composable.SwatchTimePage

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
                onClick = onSharePressed,
                icon = { Icon(Icons.Filled.Share, "Share floating action button.") },
                text = { Text(text = "Share time") },
            )
        }
    ) { innerPadding ->
        val pagerState = rememberPagerState(initialPage = 0) { 2 }
        val scope = rememberCoroutineScope()

        HorizontalPager(
            state = pagerState
        ) { currentPage ->
            if (currentPage == 0) {
                SwatchTimePage(
                    modifier = Modifier
                        .padding(innerPadding),
                    onSwitchPressed = {
                        scope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    timeState = timeState,
                )
            } else {
                LocalTimePage(
                    modifier = Modifier
                        .padding(innerPadding),
                    onSwitchPressed = {
                        scope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    timeState = timeState,
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SwatchTimeContentPreview() {
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