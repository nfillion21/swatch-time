package xyz.poolp.feature.time.composable

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import xyz.poolp.feature.time.R
import xyz.poolp.feature.time.TimeViewModel

@Composable
internal fun SwatchTimePage(
    onSwitchPressed: () -> Unit,
    timeState: TimeViewModel.TimeUiState,
    modifier: Modifier = Modifier
) = TimePage(
    onSwitchPressed = onSwitchPressed,
    timeType = TimeType.SWATCH,
    timeState = timeState,
    modifier = modifier
)

@Composable
internal fun LocalTimePage(
    onSwitchPressed: () -> Unit,
    timeState: TimeViewModel.TimeUiState,
    modifier: Modifier = Modifier
) = TimePage(
    onSwitchPressed = onSwitchPressed,
    timeType = TimeType.LOCAL,
    timeState = timeState,
    modifier = modifier
)

@Composable
private fun TimePage(
    onSwitchPressed: () -> Unit,
    timeType: TimeType,
    timeState: TimeViewModel.TimeUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(top = 32.dp)
            .fillMaxSize(),
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

            IconButton(onClick = onSwitchPressed) {
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

private enum class TimeType {
    SWATCH, LOCAL
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SwatchTimePagePreview() {
    MaterialTheme {
        Surface {
            SwatchTimePage(
                timeState = TimeViewModel.TimeUiState(
                    swatchDate = "d12.09.2024",
                    swatchTime = "@441.13"
                ),
                onSwitchPressed = {}
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LocalTimePagePreview() {
    MaterialTheme {
        Surface {
            LocalTimePage(
                timeState = TimeViewModel.TimeUiState(
                    localDate = "2024-09-13",
                    localTime = "23:09:06"
                ),
                onSwitchPressed = {}
            )
        }
    }
}
